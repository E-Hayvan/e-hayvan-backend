package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.PetOwnerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetOwnerDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.AppointmentMapper;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Mappers.PetOwnerMapper;
import com.production.ehayvanbackendapi.Repositories.PetOwnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetOwnerService {
    private final PetOwnerRepository petOwnerRepository;
    private final AppointmentService appointmentService;
    private final PetOwnerMapper petOwnerMapper;

    @Autowired
    public PetOwnerService(PetOwnerRepository petOwnerRepository,
                           PetOwnerMapper petOwnerMapper,
                           AppointmentService appointmentService) {
        this.petOwnerRepository = petOwnerRepository;
        this.petOwnerMapper = petOwnerMapper;
        this.appointmentService = appointmentService;
    }

    public List<PetOwnerDTO> getPetOwnersForVeterinarian(Integer vetId) {
        Optional<List<PetOwner>> petOwnersOptional = petOwnerRepository.getAllPetsForPetOwner(vetId);

        if (petOwnersOptional.isPresent()) {
            List<PetOwnerDTO> petOwnerDtoList = new ArrayList<>();
            for (PetOwner petOwner : petOwnersOptional.get()) {
                petOwnerDtoList.add(petOwnerMapper.convertToDto(petOwner));
            }
            return petOwnerDtoList;
        } else {
            return null;
        }
    }

    public int getAllPetOwnersCount() {
        return petOwnerRepository.getAllPetOwnersCount();
    }

    public PetOwnerDTO getPetOwnerById(Integer id) {
        Optional<PetOwner> petOwner = petOwnerRepository.findById(id);
        // Listeler otomatik olarak mapleniyor. Şimdilik bir problem yok.

        // PetOwnerDto içerisinde VeterinerDto var. Bu VeterinerDto içerisinde de
        // PetOwnerDto listesi var. Bunu engellemek için "PetOwnerMapper" dosyasında
        // Veteriner içerisindeki listelemeyi maplemeyi engelleyici bir komut yazdım.
        return petOwner.map(petOwnerMapper::convertToDto).orElse(null);
    }
    // Other service methods for updating, deleting pet owners, etc.


    public PetOwnerDTO postPetOwner(CreateOrUpdatePetOwnerDTO petOwnerDTO){
        // Map the data transfer object data to real object.
        PetOwner newOwner = petOwnerMapper.convertToEntity(petOwnerDTO);

        // Set User Type as 'Pet Owner'.
        newOwner.getUser().setUserTypeID(new UserType(1));

        // Try to save the data to the database.
        try{
            petOwnerRepository.save(newOwner);
            return petOwnerMapper.convertToDto(newOwner);
        }
        catch (Exception e){
            return null;
        }
    }

    public PetOwnerDTO updatePetOwner(Integer id, CreateOrUpdatePetOwnerDTO updatedDto){
        // Find the target entity.
        Optional<PetOwner> target = petOwnerRepository.findById(id);

        // Update and save the entity if it exists.
        if(target.isPresent()){
            PetOwner updatedTarget = petOwnerMapper.mapExistingEntity(updatedDto, target.get());
            petOwnerRepository.save(updatedTarget);
            return petOwnerMapper.convertToDto(updatedTarget);
        }

        // Return null if the entity doesn't exist.
        return null;
    }

    public PetOwnerDTO updateAssignedVeterinarian(Integer vetId, Integer petOwnerId){
        // Find the pet owner to assign the veterinarian.
        Optional<PetOwner> targetPetOwner = petOwnerRepository.findById(petOwnerId);

        // If it exists, assign the veterinarian and convert to dto.
        if(targetPetOwner.isPresent()){
            Veterinarian assignedVeterinarian = new Veterinarian();
            assignedVeterinarian.setVetID(vetId);
            targetPetOwner.get().setVet(assignedVeterinarian);

            petOwnerRepository.save(targetPetOwner.get());

            // Remove all the existing previous appointments of the pet owner.
            for(Appointment ap: targetPetOwner.get().getAppointments()){
                appointmentService.deleteAppointment(ap.getAppointmentID());
            }

            return petOwnerMapper.convertToDto(targetPetOwner.get());
        }

        // If it doesn't exist, don't assign the vet and return null.
        else
            return null;
    }

    public PetOwnerDTO deletePetOwner(Integer id){
        // Find the pet owner.
        Optional<PetOwner> target = petOwnerRepository.findById(id);

        // Delete the pet owner if it exists.
        if(target.isPresent()){
            petOwnerRepository.delete(target.get());
            PetOwnerDTO targetDto = petOwnerMapper.convertToDto(target.get());
            return targetDto;
        }
        return null;
    }
}

