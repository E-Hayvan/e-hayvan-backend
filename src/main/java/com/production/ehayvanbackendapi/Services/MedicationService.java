package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateMedicationDTO;
import com.production.ehayvanbackendapi.Entities.MedType;
import com.production.ehayvanbackendapi.Entities.Medication;
import com.production.ehayvanbackendapi.Entities.Pet;
import com.production.ehayvanbackendapi.Mappers.MedicationMapper;
import com.production.ehayvanbackendapi.Repositories.MedicationRepository;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    public MedicationService(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    public MedicationDTO getMedicationById(Integer id) {
        Medication medication = medicationRepository.findById(id).orElse(null);
        return medication != null ? medicationMapper.convertToDto(medication) : null;
    }

    public List<MedicationDTO> getAllMedications() {
        List<Medication> medicationList = medicationRepository.findAll();
        List<MedicationDTO> medicationDtoList = new ArrayList<>();

        for (Medication medication : medicationList) {
            medicationDtoList.add(medicationMapper.convertToDto(medication));
        }

        return medicationDtoList;
    }

    public MedicationDTO postMedication(CreateOrUpdateMedicationDTO medicationDto){
        // Create a new medication from the dto.
        Medication newMedication = medicationMapper.convertToEntity(medicationDto);

        // Set the assigned pet for the medication.
        Pet assignedPet = new Pet();
        assignedPet.setPetID(medicationDto.getPetID());
        newMedication.setPetID(assignedPet);

        // Set the assigned med type for the medication.
        MedType assignedMedType = new MedType();
        assignedMedType.setMedTypeID(medicationDto.getMedTypeID());
        newMedication.setMedTypeID(assignedMedType);

        // Attempt posting new medication data to DB. If fails, return null.
        try{
            medicationRepository.save(newMedication);
            MedicationDTO response = medicationMapper.convertToDto(newMedication);
            return response;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public MedicationDTO updateMedication(Integer id, CreateOrUpdateMedicationDTO updatedDto){
        // Find the existing medication.
        Optional<Medication> targetMedication = medicationRepository.findById(id);

        // If target medication is present, update it.
        if(targetMedication.isPresent()){
            Medication updatedMedication = medicationMapper.mapExistingEntity(updatedDto, targetMedication.get());

            // If pet is different, change the pet.
            if(updatedDto.getPetID() != targetMedication.get().getPetID().getPetID()){
                Pet updatedPet = new Pet();
                updatedPet.setPetID(updatedDto.getPetID());
                targetMedication.get().setPetID(updatedPet);
            }

            // If med type is different, change the med type.
            if(updatedDto.getMedTypeID() != targetMedication.get().getMedTypeID().getMedTypeID()){
                MedType updatedMedType = new MedType();
                updatedMedType.setMedTypeID(updatedDto.getMedTypeID());
                targetMedication.get().setMedTypeID(updatedMedType);
            }

            medicationRepository.save(updatedMedication);
            return medicationMapper.convertToDto(updatedMedication);
        }

        // If the target doesn't exist, return null.
        else{
            return null;
        }
    }

    public MedicationDTO deleteMedication(Integer id) {
        // Find the target medication to delete.
        Optional<Medication> targetMedication = medicationRepository.findById(id);

        // Delete medication and return DTO if it exists.
        if (targetMedication.isPresent()) {
            medicationRepository.delete(targetMedication.get());
            return medicationMapper.convertToDto(targetMedication.get());
        }
        // Return null if the target entity does not exist.
        return null;
    }

    // Other service methods for updating, deleting medications, etc.
}

