package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.MedTypeDTO;
import com.production.ehayvanbackendapi.Entities.MedType;
import com.production.ehayvanbackendapi.Mappers.MedTypeMapper;
import com.production.ehayvanbackendapi.Repositories.MedTypeRepository;
import com.production.ehayvanbackendapi.Repositories.MedicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedTypeService {
    private final MedTypeRepository medTypeRepository;
    private final MedTypeMapper medTypeMapper;

    @Autowired
    public MedTypeService(MedTypeRepository medTypeRepository, MedTypeMapper medTypeMapper) {
        this.medTypeRepository = medTypeRepository;
        this.medTypeMapper = medTypeMapper;
    }

    public MedTypeDTO getMedTypeById(Integer id) {
        MedType medType = medTypeRepository.findById(id).orElse(null);
        return medType != null ? medTypeMapper.convertToDto(medType) : null;
    }

    public List<MedTypeDTO> getAllMedTypes() {
        List<MedType> medTypeList = medTypeRepository.findAll();
        List<MedTypeDTO> medTypeDtoList = new ArrayList<>();

        for (MedType medType : medTypeList) {
            medTypeDtoList.add(medTypeMapper.convertToDto(medType));
        }

        return medTypeDtoList;
    }

    //gerekli mi bilemedim?????
    public MedTypeDTO deleteMedType(Integer id) {
        // Find the target MedType to delete.
        Optional<MedType> targetMedType = medTypeRepository.findById(id);

        // Delete MedType and return DTO if it exists.
        if (targetMedType.isPresent()) {
            medTypeRepository.delete(targetMedType.get());
            return medTypeMapper.convertToDto(targetMedType.get());
        }
        // Return null if the target entity does not exist.
        return null;
    }

}

