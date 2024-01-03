package com.production.ehayvanbackendapi.DTO.request;

public class RegisterCustomerDTO {
    private String Name;
    private String Surname;
    private String Email;
    private String Password;
    private int UserTypeID;

    public RegisterCustomerDTO(String name, String surname, String email, String password, int userTypeID) {
        Name = name;
        Surname = surname;
        Email = email;
        Password = password;
        UserTypeID = userTypeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getUserTypeID() {
        return UserTypeID;
    }

    public void setUserTypeID(int userTypeID) {
        UserTypeID = userTypeID;
    }
}
