package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.PetOwnerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetOwnerDTO;
import com.production.ehayvanbackendapi.Services.PetOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petowners")
public class PetOwnerController {
    private final PetOwnerService petOwnerService;

    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetOwnerDTO> getPetOwnerById(@PathVariable Integer id) {
        PetOwnerDTO petOwnerDTO = petOwnerService.getPetOwnerById(id);

        if (petOwnerDTO != null) {
            return new ResponseEntity<>(petOwnerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getAllPetOwnersCount() {
        int count = petOwnerService.getAllPetOwnersCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/forVet/{vetId}")
    public ResponseEntity<List<PetOwnerDTO>> getPetOwnersForVeterinarian(@PathVariable Integer vetId) {
        List<PetOwnerDTO> response = petOwnerService.getPetOwnersForVeterinarian(vetId);

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/newowner")
    public ResponseEntity<PetOwnerDTO> savePetOwner(@RequestBody CreateOrUpdatePetOwnerDTO petOwnerDTO) {
        PetOwnerDTO savedPetOwner = petOwnerService.postPetOwner(petOwnerDTO);

        if (petOwnerDTO != null){
            return new ResponseEntity<>(savedPetOwner, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PetOwnerDTO> updatePetOwner(@RequestBody CreateOrUpdatePetOwnerDTO petOwnerDTO,
                                                      @PathVariable Integer id){
        PetOwnerDTO updatedPetOwner = petOwnerService.updatePetOwner(id, petOwnerDTO);

        if(updatedPetOwner != null){
            return new ResponseEntity<>(updatedPetOwner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateVet/{vetId}/{petOwnerId}")
    public ResponseEntity<PetOwnerDTO> updateVeterinarianAssignment(@PathVariable Integer vetId,
                                                                    @PathVariable Integer petOwnerId){
        PetOwnerDTO updatedPetOwner = petOwnerService.updateAssignedVeterinarian(vetId, petOwnerId);

        if(updatedPetOwner != null){
            return new ResponseEntity<>(updatedPetOwner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetOwnerDTO> deletePetOwner(@PathVariable Integer id){
        PetOwnerDTO deletedPetOwner = petOwnerService.deletePetOwner(id);

        if(deletedPetOwner != null){
            return new ResponseEntity<>(deletedPetOwner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Other controller methods for updating, deleting pet owners, etc.
}