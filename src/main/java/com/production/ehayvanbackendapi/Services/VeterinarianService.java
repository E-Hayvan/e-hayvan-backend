package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateVeterinarianDTO;
import com.production.ehayvanbackendapi.Entities.Customer;
import com.production.ehayvanbackendapi.Entities.UserType;
import com.production.ehayvanbackendapi.Entities.Veterinarian;
import com.production.ehayvanbackendapi.Mappers.VeterinarianMapper;
import com.production.ehayvanbackendapi.Repositories.VeterinarianRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarianService {
    private final VeterinarianRepository veterinarianRepository;
    private final VeterinarianMapper veterinarianMapper;

    public VeterinarianService(VeterinarianRepository veterinarianRepository, VeterinarianMapper veterinarianMapper) {
        this.veterinarianRepository = veterinarianRepository;
        this.veterinarianMapper = veterinarianMapper;
    }

    public int getAllVetsCount() {
        return veterinarianRepository.getAllVetsCount();
    }

    public VeterinarianDTO getVeterinarianById(Integer id) {
        Veterinarian veterinarian = veterinarianRepository.findById(id).orElse(null);
        return veterinarian != null ? veterinarianMapper.convertToDto(veterinarian) : null;
    }

    public List<VeterinarianDTO> getVeterinariansByClinic(String name){
        // Get all veterinarians using the given filter
        List<Veterinarian> targetVeterinarians = veterinarianRepository.findVeterinariansByName(name);

        // If result is empty, return all veterinarians
        if(targetVeterinarians.isEmpty()){
            return null;
        }

        // Else, return the list of veterinarians
        else{
            List<VeterinarianDTO> listOfDtos = targetVeterinarians.stream().map(veterinarianMapper::convertToDto).collect(Collectors.toList());
            return listOfDtos;
        }
    }

    public VeterinarianDTO postVeterinarian(CreateOrUpdateVeterinarianDTO newDto){
        // Map the data transfer object data to real object.
        Veterinarian newVet = veterinarianMapper.convertToEntity(newDto);

        // Set the user type as 'Veterinarian'.
        newVet.getUser().setUserTypeID(new UserType(2));

        // Try to save the data to the database.
        try{
            veterinarianRepository.save(newVet);
            return veterinarianMapper.convertToDto(newVet);
        }
        catch (Exception e){
            return null;
        }
    }

    public VeterinarianDTO updateVeterinarian(Integer id, CreateOrUpdateVeterinarianDTO updatedDto){
        // Find the target veterinarian.
        Optional<Veterinarian> target = veterinarianRepository.findById(id);

        // If the entity exists, update the entity with the new dto.
        if(target.isPresent()){
            Veterinarian updatedVet = veterinarianMapper.mapExistingEntity(target.get(), updatedDto);
            veterinarianRepository.save(updatedVet);
            return veterinarianMapper.convertToDto(updatedVet);
        }

        // If it doesn't exist return null.
        else{
            return null;
        }
    }

    public VeterinarianDTO deleteVeterinarian(Integer id){
        // Find the entity to remove.
        Optional<Veterinarian> target = veterinarianRepository.findById(id);

        // If the entity exists, remove from the database and return dto.
        if(target.isPresent()){
            veterinarianRepository.delete(target.get());
            return veterinarianMapper.convertToDto(target.get());
        }

        // If it doesn't exist, return null.
        else{
            return null;
        }
    }

    public List<VeterinarianDTO> getAllVeterinarians() {
        List<Veterinarian> veterinarianList = veterinarianRepository.findAll();
        List<VeterinarianDTO> veterinarianDtoList = new ArrayList<>();

        for (Veterinarian veterinarian : veterinarianList) {
            veterinarianDtoList.add(veterinarianMapper.convertToDto(veterinarian));
        }

        return veterinarianDtoList;
    }

    // Other service methods for updating, deleting veterinarians, etc.
}

