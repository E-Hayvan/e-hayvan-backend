package com.production.ehayvanbackendapi.DTO;

import com.production.ehayvanbackendapi.Entities.Schedule;

public class MedicationDTO {
    private Integer medicationID;
    private String medicationName;
    private Integer medTypeID;
    private ScheduleDTO schedule;
    private Integer petID;

    public MedicationDTO(){

    }
    public MedicationDTO(Integer medicationID, String medicationName, Integer
            medTypeID, ScheduleDTO scheduleID, Integer petID) {
        this.medicationID = medicationID;
        this.medicationName = medicationName;
        this.medTypeID = medTypeID;
        this.schedule = scheduleID;
        this.petID = petID;
    }
    public Integer getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(Integer medicationID) {
        this.medicationID = medicationID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Integer getMedTypeID() {
        return medTypeID;
    }

    public void setMedTypeID(Integer medTypeID) {
        this.medTypeID = medTypeID;
    }

    public ScheduleDTO getScheduleID() {
        return schedule;
    }

    public void setScheduleID(ScheduleDTO schedule) {
        this.schedule = schedule;
    }

    public Integer getPetID() {
        return petID;
    }

    public void setPetID(Integer petID) {
        this.petID = petID;
    }
}
