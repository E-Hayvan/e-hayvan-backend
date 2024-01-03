package com.production.ehayvanbackendapi.DTO;

import java.util.List;

public class PetOwnerDTO {
    private Integer petOwnerID;
    private CustomerDTO user;
    private List<PetDTO> pets;
    private String vetName;
    private Integer vetID;
    private List<AppointmentDTO> appointments;
    public PetOwnerDTO(){

    }
    public PetOwnerDTO(Integer petOwnerID, CustomerDTO user, List<PetDTO> pets,
                       String vetName, Integer vetID, List<AppointmentDTO> appointments) {
        this.petOwnerID = petOwnerID;
        this.user = user;
        this.pets = pets;
        this.appointments = appointments;
        this.vetName = vetName;
        this.vetID = vetID;
    }


    public Integer getPetOwnerID() {
        return petOwnerID;
    }

    public void setPetOwnerID(Integer petOwnerID) {
        this.petOwnerID = petOwnerID;
    }

    public CustomerDTO getUser() {
        return user;
    }

    public void setUser(CustomerDTO user) {
        this.user = user;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }


    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public Integer getVetID() {
        return vetID;
    }

    public void setVetID(Integer vetID) {
        this.vetID = vetID;
    }
}

