package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.PetTypeDTO;
import com.production.ehayvanbackendapi.Services.PetTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pettypes")
public class PetTypeController {
    private final PetTypeService petTypeService;

    public PetTypeController(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetTypeDTO> getPetTypeById(@PathVariable Integer id) {
        PetTypeDTO petTypeDTO = petTypeService.getPetTypeById(id);

        if (petTypeDTO != null) {
            return new ResponseEntity<>(petTypeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // no other CRUD operation is necessary


}

