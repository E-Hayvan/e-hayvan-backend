package com.production.ehayvanbackendapi.Mappers;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.CustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateAppointmentDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetOwnerDTO;
import com.production.ehayvanbackendapi.Entities.Appointment;
import com.production.ehayvanbackendapi.Entities.Customer;
import com.production.ehayvanbackendapi.Entities.PetOwner;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    private final ModelMapper modelMapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        this.modelMapper.createTypeMap(Appointment.class, AppointmentDTO.class).addMappings(
                mapper -> {
                    mapper.map(src -> src.getPetID().getPetID(), AppointmentDTO::setPetID);
                    mapper.map(src -> src.getVetID().getVetID(), AppointmentDTO::setVetID);
                    mapper.map(src -> src.getPetOwnerID().getPetOwnerID(), AppointmentDTO::setPetOwnerID);
                }
        );
    }

    public AppointmentDTO convertToDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    public Appointment convertToEntity(CreateOrUpdateAppointmentDTO appointmentDTO) {
        return modelMapper.map(appointmentDTO, Appointment.class);
    }
    public Appointment mapExistingEntity(CreateOrUpdateAppointmentDTO appointmentDTO, Appointment target){
        modelMapper.map(appointmentDTO, target);
        return target;
    }
}
