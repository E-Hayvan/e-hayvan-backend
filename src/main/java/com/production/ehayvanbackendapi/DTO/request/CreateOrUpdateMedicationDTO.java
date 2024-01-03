package com.production.ehayvanbackendapi.DTO.request;

import com.production.ehayvanbackendapi.DTO.MedicationDTO;

public class CreateOrUpdateMedicationDTO {
    private String medicationName;
    private Integer medTypeID;
    private CreateOrUpdateScheduleDTO scheduleID;
    private Integer petID;

    public CreateOrUpdateMedicationDTO(String medicationName, Integer medTypeID, CreateOrUpdateScheduleDTO scheduleID, Integer petID) {
        this.medicationName = medicationName;
        this.medTypeID = medTypeID;
        this.scheduleID = scheduleID;
        this.petID = petID;
    }

    public CreateOrUpdateMedicationDTO(MedicationDTO medicationDTO) {
        this.medicationName = medicationDTO.getMedicationName();
        this.medTypeID = medicationDTO.getMedTypeID();
        this.scheduleID = new CreateOrUpdateScheduleDTO(medicationDTO.getScheduleID());
        this.petID = medicationDTO.getPetID();
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

    public Integer getPetID() {
        return petID;
    }

    public void setPetID(Integer petID) {
        this.petID = petID;
    }

    public CreateOrUpdateScheduleDTO getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(CreateOrUpdateScheduleDTO scheduleID) {
        this.scheduleID = scheduleID;
    }
}
