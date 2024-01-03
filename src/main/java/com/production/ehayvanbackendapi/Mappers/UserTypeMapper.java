package com.production.ehayvanbackendapi.Mappers;

import com.production.ehayvanbackendapi.DTO.UserTypeDTO;
import com.production.ehayvanbackendapi.Entities.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper {
    private final ModelMapper modelMapper;

    public UserTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        //eklenecek mi??
    }

    public UserTypeDTO convertToDto(UserType userType) {
        return modelMapper.map(userType, UserTypeDTO.class);
    }

    public UserType convertToEntity(UserTypeDTO userTypeDTO) {
        return modelMapper.map(userTypeDTO, UserType.class);
    }
}

