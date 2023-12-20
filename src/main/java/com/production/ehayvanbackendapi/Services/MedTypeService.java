package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.MedTypeDTO;
import com.production.ehayvanbackendapi.Entities.MedType;
import com.production.ehayvanbackendapi.Mappers.MedTypeMapper;
import com.production.ehayvanbackendapi.Repositories.MedTypeRepository;
import com.production.ehayvanbackendapi.Repositories.MedicationRepository;

import java.util.List;

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

}

