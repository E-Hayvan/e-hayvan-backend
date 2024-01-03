package com.production.ehayvanbackendapi.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="Medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicationID;
    @Column(nullable = false)
    private String medicationName;
    @ManyToOne
    @JoinColumn(name="MedTypeID", referencedColumnName = "MedTypeID")
    private MedType MedTypeID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ScheduleID", referencedColumnName = "ScheduleID")
    private Schedule ScheduleID;
    @ManyToOne
    @JoinColumn(name="PetID", referencedColumnName = "PetID")
    private Pet PetID;

    public Pet getPetID() {
        return PetID;
    }
    public void setPetID(Pet petID) {
        PetID = petID;
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
    public MedType getMedTypeID() {
        return MedTypeID;
    }
    public void setMedTypeID(MedType medTypeID) {
        MedTypeID = medTypeID;
    }
    public Schedule getScheduleID() {
        return ScheduleID;
    }
    public void setScheduleID(Schedule scheduleID) {
        ScheduleID = scheduleID;
    }
}
