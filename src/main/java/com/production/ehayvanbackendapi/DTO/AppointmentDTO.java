package com.production.ehayvanbackendapi.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class AppointmentDTO {
    private Integer appointmentID;
    private Integer petOwnerID;
    private Integer vetID;
    private Integer petID;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime appointmentDate;

    public AppointmentDTO(){

    }
    public AppointmentDTO(Integer appointmentID, Integer petOwnerID, Integer vetID, Integer petID, LocalDateTime appointmentDate) {
        this.appointmentID = appointmentID;
        this.petOwnerID = petOwnerID;
        this.vetID = vetID;
        this.petID = petID;
        this.appointmentDate = appointmentDate;
    }

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Integer getPetOwnerID() {
        return petOwnerID;
    }

    public void setPetOwnerID(Integer petOwnerID) {
        this.petOwnerID = petOwnerID;
    }

    public Integer getVetID() {
        return vetID;
    }

    public void setVetID(Integer vetID) {
        this.vetID = vetID;
    }

    public Integer getPetID() {
        return petID;
    }

    public void setPetID(Integer petID) {
        this.petID = petID;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
