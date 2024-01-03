package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.MedTypeDTO;
import com.production.ehayvanbackendapi.Services.MedTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medtypes")
public class MedTypeController {
    private final MedTypeService medTypeService;

    public MedTypeController(MedTypeService medTypeService) {
        this.medTypeService = medTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedTypeDTO> getMedTypeById(@PathVariable Integer id) {
        MedTypeDTO medTypeDTO = medTypeService.getMedTypeById(id);

        if (medTypeDTO != null) {
            return new ResponseEntity<>(medTypeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedTypeDTO>> getAllMedTypes() {
        List<MedTypeDTO> response = medTypeService.getAllMedTypes();

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedTypeDTO> deleteMedType(@PathVariable Integer id) {
        MedTypeDTO deletedMedType = medTypeService.deleteMedType(id);
        if (deletedMedType != null) {
            return new ResponseEntity<>(deletedMedType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}



