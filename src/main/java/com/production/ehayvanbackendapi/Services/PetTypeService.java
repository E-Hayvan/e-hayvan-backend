package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.PetTypeDTO;
import com.production.ehayvanbackendapi.Entities.PetType;
import com.production.ehayvanbackendapi.Mappers.PetTypeMapper;
import com.production.ehayvanbackendapi.Repositories.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetTypeService {
    private final PetTypeRepository petTypeRepository;
    private final PetTypeMapper petTypeMapper;

    public PetTypeService(PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
        this.petTypeRepository = petTypeRepository;
        this.petTypeMapper = petTypeMapper;
    }
    public PetTypeDTO getPetTypeById(Integer id) {
        PetType petType = petTypeRepository.findById(id).orElse(null);
        return petType != null ? petTypeMapper.convertToDto(petType) : null;
    }

    public List<PetTypeDTO> getAllPetTypes() {
        List<PetType> petTypeList = petTypeRepository.findAll();
        List<PetTypeDTO> petTypeDtoList = new ArrayList<>();

        for (PetType petType : petTypeList) {
            petTypeDtoList.add(petTypeMapper.convertToDto(petType));
        }

        return petTypeDtoList;
    }
}
