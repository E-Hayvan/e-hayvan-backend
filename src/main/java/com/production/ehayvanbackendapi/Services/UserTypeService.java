package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.UserTypeDTO;
import com.production.ehayvanbackendapi.Entities.UserType;
import com.production.ehayvanbackendapi.Mappers.UserTypeMapper;
import com.production.ehayvanbackendapi.Repositories.UserTypeRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeService {
    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    public UserTypeDTO getUserTypeById(Integer id) {
        UserType userType = userTypeRepository.findById(id).orElse(null);
        return userType != null ? userTypeMapper.convertToDto(userType) : null;
    }

    public List<UserTypeDTO> getAllUserTypes() {
        List<UserType> userTypeList = userTypeRepository.findAll();
        List<UserTypeDTO> userTypeDtoList = new ArrayList<>();

        for (UserType userType : userTypeList) {
            userTypeDtoList.add(userTypeMapper.convertToDto(userType));
        }

        return userTypeDtoList;
    }


    // Other service methods for updating, deleting user types, etc.
}

