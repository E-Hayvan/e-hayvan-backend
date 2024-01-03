package com.production.ehayvanbackendapi.DTO;

public class CustomerDTO {
    private Integer userID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Integer userTypeID;
    private Integer vetID;
    private Integer ownerID;

    public CustomerDTO(){

    }
    public CustomerDTO(Integer userID, String name, String surname, String email,
                       String password, Integer userTypeID, Integer vetID, Integer ownerID) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.userTypeID = userTypeID;
        this.vetID = vetID;
        this.ownerID = ownerID;
    }



    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(Integer userTypeID) {
        this.userTypeID = userTypeID;
    }

    public Integer getVetID() {
        return vetID;
    }

    public void setVetID(Integer vetID) {
        this.vetID = vetID;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }
}

