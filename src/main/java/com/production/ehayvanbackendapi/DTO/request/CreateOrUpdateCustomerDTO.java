package com.production.ehayvanbackendapi.DTO.request;

import com.production.ehayvanbackendapi.DTO.CustomerDTO;

public class CreateOrUpdateCustomerDTO {
    private String Name;
    private String Surname;
    private String Email;
    private String Password;

    // added for testing at the moment
    public CreateOrUpdateCustomerDTO(String Name, String Surname, String Email, String Password) {
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
        this.Password = Password;
    }

    public CreateOrUpdateCustomerDTO(CustomerDTO customerDTO) {
        this.Name = customerDTO.getName();
        this.Surname = customerDTO.getSurname();
        this.Email = customerDTO.getEmail();
        this.Password = customerDTO.getPassword();
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
}
