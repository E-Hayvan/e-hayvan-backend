package com.production.ehayvanbackendapi.DTO.request;

import com.production.ehayvanbackendapi.DTO.PetOwnerDTO;

public class CreateOrUpdatePetOwnerDTO {
    private CreateOrUpdateCustomerDTO user;

    // added for testing at the moment
    public CreateOrUpdatePetOwnerDTO() {
        this.user = null;
    }

    public CreateOrUpdatePetOwnerDTO(PetOwnerDTO petOwnerDTO) {
        this.user = new CreateOrUpdateCustomerDTO(petOwnerDTO.getUser());
    }

    // added for testing at the moment
    public CreateOrUpdatePetOwnerDTO(CreateOrUpdateCustomerDTO user) {
        this.user = user;
    }
  
    public CreateOrUpdateCustomerDTO getUser() {
        return user;
    }

    public void setUser(CreateOrUpdateCustomerDTO user) {
        this.user = user;
    }
}
