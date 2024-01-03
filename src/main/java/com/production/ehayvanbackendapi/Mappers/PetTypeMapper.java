package com.production.ehayvanbackendapi.Mappers;

import com.production.ehayvanbackendapi.DTO.PetTypeDTO;
import com.production.ehayvanbackendapi.Entities.PetType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PetTypeMapper {
    private final ModelMapper modelMapper;

    public PetTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PetTypeDTO convertToDto(PetType petType) {
        return modelMapper.map(petType, PetTypeDTO.class);
    }

    public PetType convertToEntity(PetTypeDTO petTypeDTO) {
        return modelMapper.map(petTypeDTO, PetType.class);
    }
}

