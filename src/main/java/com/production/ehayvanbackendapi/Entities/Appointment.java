package com.production.ehayvanbackendapi.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer AppointmentID;
    @ManyToOne
    @JoinColumn(name = "PetOwnerID", referencedColumnName = "PetOwnerID")
    private PetOwner PetOwnerID;
    @ManyToOne
    @JoinColumn(name = "VetID", referencedColumnName = "VetID")
    private Veterinarian VetID;
    @ManyToOne
    @JoinColumn(name = "PetID", referencedColumnName = "PetID")
    private Pet PetID;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime AppointmentDate;
    public int getAppointmentID() {
        return AppointmentID;
    }
    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }
    public PetOwner getPetOwnerID() {
        return PetOwnerID;
    }
    public void setPetOwnerID(PetOwner petOwnerID) {
        PetOwnerID = petOwnerID;
    }
    public Veterinarian getVetID() {
        return VetID;
    }
    public void setVetID(Veterinarian vetID) {
        VetID = vetID;
    }
    public Pet getPetID() {
        return PetID;
    }
    public void setPetID(Pet petID) {
        PetID = petID;
    }
    public LocalDateTime getAppointmentDate() {
        return AppointmentDate;
    }
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        AppointmentDate = appointmentDate;
    }
}
