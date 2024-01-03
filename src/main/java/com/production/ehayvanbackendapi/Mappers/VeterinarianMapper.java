package com.production.ehayvanbackendapi.Mappers;

import com.production.ehayvanbackendapi.DTO.PetOwnerDTO;
import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateVeterinarianDTO;
import com.production.ehayvanbackendapi.Entities.PetOwner;
import com.production.ehayvanbackendapi.Entities.Veterinarian;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VeterinarianMapper {
    private final ModelMapper modelMapper;

    public VeterinarianMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VeterinarianDTO convertToDto(Veterinarian veterinarian) {
        return modelMapper.map(veterinarian, VeterinarianDTO.class);
    }

    public Veterinarian convertToEntity(CreateOrUpdateVeterinarianDTO veterinarianDTO) {
        return modelMapper.map(veterinarianDTO, Veterinarian.class);
    }

    public Veterinarian mapExistingEntity(Veterinarian vet, CreateOrUpdateVeterinarianDTO updatedDto){
        modelMapper.map(updatedDto, vet);
        return vet;
    }
}

