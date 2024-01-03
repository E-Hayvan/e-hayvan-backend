package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateAppointmentDTO;
import com.production.ehayvanbackendapi.Entities.Appointment;
import com.production.ehayvanbackendapi.Mappers.AppointmentMapper;
import com.production.ehayvanbackendapi.Services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Integer id) {
        AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(id);

        if (appointmentDTO != null) {
            return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> response = appointmentService.getAllAppointments();

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all/{petOwnerId}")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForPetOwner(@PathVariable Integer petOwnerId) {
        List<AppointmentDTO> response = appointmentService.getAllAppointmentsForPetOwner(petOwnerId);

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/veterinarian/{vetId}")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForVeterinarian(@PathVariable Integer vetId) {
        List<AppointmentDTO> response = appointmentService.getAllAppointmentsForVeterinarian(vetId);

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> saveAppointment(@RequestBody CreateOrUpdateAppointmentDTO appointmentDTO) {
        AppointmentDTO createdAppointment = appointmentService.postAppointment(appointmentDTO);
        if(createdAppointment != null){
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } else {
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id,
                                                            @RequestBody CreateOrUpdateAppointmentDTO updatedDto){
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, updatedDto);
        if(updatedAppointment != null){
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTO> deleteAppointment(@PathVariable Integer id) {
        AppointmentDTO deletedAppointment = appointmentService.deleteAppointment(id);
        if (deletedAppointment != null) {
            return new ResponseEntity<>(deletedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Other controller methods for creating, updating, and deleting appointments
}
