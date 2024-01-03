package com.production.ehayvanbackendapi.DTO;

import java.util.List;

public class VeterinarianDTO {
    private Integer vetID;
    private List<PetOwnerDTO> petOwners;
    private String clinic;
    private CustomerDTO user;
    private List<AppointmentDTO> appointments;
    public VeterinarianDTO(){

    }
    public VeterinarianDTO(Integer vetID, List<PetOwnerDTO> petOwners, String clinic,
                           CustomerDTO user, List<AppointmentDTO> appointments) {
        this.vetID = vetID;
        this.petOwners = petOwners;
        this.clinic = clinic;
        this.user = user;
        this.appointments = appointments;
    }

    public Integer getVetID() {
        return vetID;
    }

    public void setVetID(Integer vetID) {
        this.vetID = vetID;
    }

    public List<PetOwnerDTO> getPetOwners() {
        return petOwners;
    }

    public void setPetOwners(List<PetOwnerDTO> petOwners) {
        this.petOwners = petOwners;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public CustomerDTO getUser() {
        return user;
    }

    public void setUser(CustomerDTO user) {
        this.user = user;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }
}

