package com.production.ehayvanbackendapi.DTO.request;

import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;

public class CreateOrUpdateVeterinarianDTO {
    private CreateOrUpdateCustomerDTO user;
    private String clinic;

    public CreateOrUpdateVeterinarianDTO(CreateOrUpdateCustomerDTO user, String clinic) {
        this.user = user;
        this.clinic = clinic;
    }

    public CreateOrUpdateVeterinarianDTO(VeterinarianDTO veterinarianDTO) {
        this.user = new CreateOrUpdateCustomerDTO(veterinarianDTO.getUser());
        this.clinic = veterinarianDTO.getClinic();
    }

    public CreateOrUpdateCustomerDTO getUser() {
        return user;
    }

    public void setUser(CreateOrUpdateCustomerDTO user) {
        this.user = user;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }
}
