package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.Services.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<MedicationDTO> getMedicationById(@PathVariable Integer id) {
        MedicationDTO medicationDTO = medicationService.getMedicationById(id);

        if (medicationDTO != null) {
            return new ResponseEntity<>(medicationDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<MedicationDTO> saveMedication(@RequestBody MedicationDTO medicationDTO) {
        MedicationDTO savedMedication = medicationService.saveMedication(medicationDTO);
        return new ResponseEntity<>(savedMedication, HttpStatus.CREATED);
    }
}