package com.production.ehayvanbackendapi.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="MedType")
public class MedType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MedTypeID;
    @Column(nullable = false)
    private String MedType;
    @OneToMany(mappedBy = "MedTypeID")
    private List<Medication> medications;

    public Integer getMedTypeID() {
        return MedTypeID;
    }
    public void setMedTypeID(Integer medTypeID) {
        MedTypeID = medTypeID;
    }
    public String getMedType() {
        return MedType;
    }
    public void setMedType(String medType) {
        MedType = medType;
    }
    public List<Medication> getMedications() {
        return medications;
    }
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
