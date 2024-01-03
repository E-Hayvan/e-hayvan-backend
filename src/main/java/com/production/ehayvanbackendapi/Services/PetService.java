package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.AppointmentMapper;
import com.production.ehayvanbackendapi.Mappers.MedicationMapper;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Repositories.PetRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Autowired
    public PetService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    public int getAllPetsCount() {
        return petRepository.getAllPetsCount();
    }

    public PetDTO getPetById(Integer id) {
        // Find pet if exists.
        Optional<Pet> pet = petRepository.findById(id);

        // Convert to dto if it exists.
        if(pet.isPresent()){
            return petMapper.convertToDto(pet.get());
        }

        // Return null if it doesn't exist.
        else
            return null;
    }

    public List<PetDTO> getAllPets() {
        List<Pet> petList = petRepository.findAll();
        List<PetDTO> petDtoList = new ArrayList<>();

        for (Pet pet : petList) {
            petDtoList.add(petMapper.convertToDto(pet));
        }

        return petDtoList;
    }


    public PetDTO postPet(CreateOrUpdatePetDTO newPetDto){
        // Map the data transfer object data to real object.
        Pet newPet = petMapper.convertToEntity(newPetDto);

        // Set the petOwner of newly created pet.
        PetOwner ownerOfPet = new PetOwner();
        ownerOfPet.setPetOwnerID(newPetDto.getPetOwnerID());
        newPet.setPetOwnerID(ownerOfPet);

        // Set the petType of newly created pet.
        PetType petType = new PetType();
        petType.setPetTypeID(newPetDto.getPetTypeID());
        newPet.setPetTypeID(petType);

        try{
            petRepository.save(newPet);
            return petMapper.convertToDto(newPet);
        }
        catch (Exception e){
            return null;
        }
    }

    public PetDTO updatePet(Integer id, CreateOrUpdatePetDTO updatedDto){
        // Find the pet to update.
        Optional<Pet> target = petRepository.findById(id);

        // Update the pet if it exists.
        if(target.isPresent()){
            Pet updatedPet = petMapper.mapExistingEntity(updatedDto, target.get());

            // Check if the petOwner has changed.
            if(updatedPet.getPetOwnerID().getPetOwnerID() != updatedDto.getPetOwnerID()){
                PetOwner newPetOwner = new PetOwner();
                newPetOwner.setPetOwnerID(updatedDto.getPetOwnerID());
                updatedPet.setPetOwnerID(newPetOwner);
            }

            // Check if the petType has changed.
            if(updatedPet.getPetTypeID().getPetTypeID() != updatedDto.getPetTypeID()){
                PetType newPetType = new PetType();
                newPetType.setPetTypeID(updatedDto.getPetTypeID());
                updatedPet.setPetTypeID(newPetType);
            }

            // Save the pet and return the dto.
            petRepository.save(updatedPet);
            return petMapper.convertToDto(updatedPet);
        }

        // Return null if target entity does not exist.
        else
            return null;
    }

    public PetDTO deletePet(Integer id){
        // Find the target pet to delete.
        Optional<Pet> targetPet = petRepository.findById(id);

        // Delete pet and return dto if it exists.
        if(targetPet.isPresent()){
            petRepository.delete(targetPet.get());
            return petMapper.convertToDto(targetPet.get());
        }

        // Return null if target entity does not exist.
        return null;
    }

    public List<PetDTO> getAllPetsForPetOwner(Integer petOwnerId) {
        Optional<List<Pet>> petsOptional = petRepository.getAllPetsForPetOwner(petOwnerId);

        if (petsOptional.isPresent()) {
            List<PetDTO> petDtoList = new ArrayList<>();
            for (Pet pet : petsOptional.get()) {
                petDtoList.add(petMapper.convertToDto(pet));
            }
            return petDtoList;
        } else {
            return null;
        }
    }
}

