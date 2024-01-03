package com.production.ehayvanbackendapi.Mappers;

import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.ScheduleDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateMedicationDTO;
import com.production.ehayvanbackendapi.Entities.Medication;
import com.production.ehayvanbackendapi.Entities.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MedicationMapper {
    private final ModelMapper modelMapper;

    public MedicationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Medication.class, MedicationDTO.class).addMappings(
                mapper -> {
                    mapper.map(src -> src.getPetID().getPetID(), MedicationDTO::setPetID);
                    mapper.map(src -> src.getMedTypeID().getMedTypeID(), MedicationDTO::setMedTypeID);
                    mapper.map(src -> src.getScheduleID().getScheduleID(), MedicationDTO::setScheduleID);
                    mapper.map(Medication::getScheduleID, MedicationDTO::setScheduleID);
                }
        );
        this.modelMapper.createTypeMap(CreateOrUpdateMedicationDTO.class, Medication.class).addMappings(
                mapper -> {
                    mapper.skip(CreateOrUpdateMedicationDTO::getScheduleID,
                            (dest, v) -> dest.getScheduleID().setScheduleID((Integer) v));
                    mapper.skip(src -> src.getPetID(),
                            (dest, v) -> dest.getPetID().setPetID((Integer) v));
                    mapper.skip(src -> src.getMedTypeID(),
                            (dest, v) -> dest.getMedTypeID().setMedTypeID((Integer) v));
                    mapper.skip(src -> src.getMedTypeID(),
                            (dest, v) -> dest.getMedTypeID().setMedType((String) v));
                    mapper.map(CreateOrUpdateMedicationDTO::getScheduleID, Medication::setScheduleID);
                    mapper.skip(Medication::setMedicationID);
                }
        );
    }

    public MedicationDTO convertToDto(Medication medication) {
        return modelMapper.map(medication, MedicationDTO.class);
    }

    public Medication convertToEntity(CreateOrUpdateMedicationDTO medicationDTO) {
        return modelMapper.map(medicationDTO, Medication.class);
    }

    public Medication mapExistingEntity(CreateOrUpdateMedicationDTO updatedDTO, Medication target){
        modelMapper.map(updatedDTO, target);
        return target;
    }
}

