package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateAppointmentDTO;
import com.production.ehayvanbackendapi.Entities.Appointment;
import com.production.ehayvanbackendapi.Entities.Pet;
import com.production.ehayvanbackendapi.Entities.PetOwner;
import com.production.ehayvanbackendapi.Entities.Veterinarian;
import com.production.ehayvanbackendapi.Mappers.AppointmentMapper;
import com.production.ehayvanbackendapi.Repositories.AppointmentRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }
    public AppointmentDTO getAppointmentById(Integer id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        return appointment != null ? appointmentMapper.convertToDto(appointment) : null;
    }

    public AppointmentDTO postAppointment(CreateOrUpdateAppointmentDTO appointmentDto){
        // Map the appointment dto to appointment.
        Appointment newAppointment = appointmentMapper.convertToEntity(appointmentDto);

        // Try saving the new appointment.
        try{
            appointmentRepository.save(newAppointment);
            return appointmentMapper.convertToDto(newAppointment);
        }
        catch (Exception e){
            return null;
        }
    }

    public AppointmentDTO updateAppointment(Integer id, CreateOrUpdateAppointmentDTO updatedDto){
        // Find the target appointment.
        Optional<Appointment> target = appointmentRepository.findById(id);

        // If the target exists, overwrite new dto onto the target.
        if(target.isPresent()){
            Appointment updatedTarget = appointmentMapper.mapExistingEntity(updatedDto, target.get());
            appointmentRepository.save(updatedTarget);
            return appointmentMapper.convertToDto(updatedTarget);
        }
        else
            return null;
    }

    public AppointmentDTO deleteAppointment(Integer id) {
        // Find the target appointment to delete.
        Optional<Appointment> targetAppointment = appointmentRepository.findById(id);

        // Delete appointment and return DTO if it exists.
        if (targetAppointment.isPresent()) {
            appointmentRepository.delete(targetAppointment.get());
            return appointmentMapper.convertToDto(targetAppointment.get());
        }
        // Return null if the target entity does not exist.
        return null;
    }

//    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
//        Appointment appointment = appointmentMapper.convertToEntity(appointmentDTO);
//        Appointment savedAppointment = appointmentRepository.save(appointment);
//        return appointmentMapper.convertToDto(savedAppointment);
//    }
    // Other service methods for creating, updating, and deleting appointments
}

