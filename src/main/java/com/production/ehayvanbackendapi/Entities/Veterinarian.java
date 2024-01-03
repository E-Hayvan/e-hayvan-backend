package com.production.ehayvanbackendapi.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Veterinarian")
public class Veterinarian{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer VetID;
    @OneToMany(mappedBy = "Vet")
    private List<PetOwner> PetOwners;
    @Column(nullable = false)
    private String Clinic;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private Customer User;
    @OneToMany(mappedBy = "VetID")
    private List<Appointment> Appointments;

    public String getClinic() {
        return Clinic;
    }
    public void setClinic(String clinic) {
        Clinic = clinic;
    }
    public Integer getVetID() {
        return VetID;
    }
    public void setVetID(Integer vetID) {
        VetID = vetID;
    }
    public List<PetOwner> getPetOwners() {
        return PetOwners;
    }
    public void setPetOwners(List<PetOwner> petOwners) {
        PetOwners = petOwners;
    }
    public Customer getUser() {
        return User;
    }
    public void setUser(Customer user) {
        User = user;
    }
    public List<Appointment> getAppointments() {
        return Appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        Appointments = appointments;
    }
}
