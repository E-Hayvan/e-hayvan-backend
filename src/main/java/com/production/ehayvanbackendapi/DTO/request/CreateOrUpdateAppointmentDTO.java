package com.production.ehayvanbackendapi.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class CreateOrUpdateAppointmentDTO {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime appointmentDate;
    private Integer petID;
    private Integer vetID;
    private Integer petOwnerID;

    public CreateOrUpdateAppointmentDTO(){

    }

    public CreateOrUpdateAppointmentDTO(AppointmentDTO appointmentDTO) {
        this.petID = appointmentDTO.getPetID();
        this.vetID = appointmentDTO.getVetID();
        this.petOwnerID = appointmentDTO.getPetOwnerID();
        this.appointmentDate = appointmentDTO.getAppointmentDate();
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getPetID() {
        return petID;
    }

    public void setPetID(Integer petID) {
        this.petID = petID;
    }

    public Integer getVetID() {
        return vetID;
    }

    public void setVetID(Integer vetID) {
        this.vetID = vetID;
    }

    public Integer getPetOwnerID() {
        return petOwnerID;
    }

    public void setPetOwnerID(Integer petOwnerID) {
        this.petOwnerID = petOwnerID;
    }
}
