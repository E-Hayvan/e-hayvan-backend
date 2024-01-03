package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Integer id) {
        PetDTO petDTO = petService.getPetById(id);

        if (petDTO != null) {
            return new ResponseEntity<>(petDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getAllPetsCount() {
        int count = petService.getAllPetsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/all/{petOwnerId}")
    public ResponseEntity<List<PetDTO>> getAllPetsForPetOwner(@PathVariable Integer petOwnerId) {
        List<PetDTO> response = petService.getAllPetsForPetOwner(petOwnerId);

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> response = petService.getAllPets();

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody CreateOrUpdatePetDTO petDTO) {
        PetDTO savedPet = petService.postPet(petDTO);
        if(savedPet != null){
            return new ResponseEntity<>(savedPet, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Integer id, @RequestBody CreateOrUpdatePetDTO petDTO){
        PetDTO updatedPet = petService.updatePet(id, petDTO);
        if(updatedPet != null){
            return new ResponseEntity<>(updatedPet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetDTO> deletePet(@PathVariable Integer id){
        PetDTO deletedPet = petService.deletePet(id);
        if(deletedPet != null){
            return new ResponseEntity<>(deletedPet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Other controller methods for updating, deleting pets, etc.
}

