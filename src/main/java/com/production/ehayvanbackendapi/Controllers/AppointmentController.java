package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTO> deleteAppointment(@PathVariable Integer id) {
        AppointmentDTO deletedAppointment = appointmentService.deleteAppointment(id);
        if (deletedAppointment != null) {
            return new ResponseEntity<>(deletedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping
//    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
//        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
//        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
//    }

    // Other controller methods for creating, updating, and deleting appointments
}
