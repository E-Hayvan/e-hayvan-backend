package com.production.ehayvanbackendapi.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PetType")
public class PetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer PetTypeID;
    @Column(nullable = false)
    private String Type;
    @OneToMany(mappedBy = "PetTypeID")
    private List<Pet> pets;

    public Integer getPetTypeID() {
        return PetTypeID;
    }
    public void setPetTypeID(Integer petTypeID) {
        PetTypeID = petTypeID;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public List<Pet> getPets() {
        return pets;
    }
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
