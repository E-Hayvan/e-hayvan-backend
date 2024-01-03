package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateMedicationDTO;
import com.production.ehayvanbackendapi.Services.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
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

    @GetMapping("/all")
    public ResponseEntity<List<MedicationDTO>> getAllMedications() {
        List<MedicationDTO> response = medicationService.getAllMedications();

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<MedicationDTO> postMedication(@RequestBody CreateOrUpdateMedicationDTO medicationDto){
        MedicationDTO postedMedication = medicationService.postMedication(medicationDto);

        if(postedMedication != null){
            return new ResponseEntity<>(postedMedication, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MedicationDTO> updateMedication(@PathVariable Integer id,
                                                          @RequestBody CreateOrUpdateMedicationDTO updatedDto){
        MedicationDTO updatedMedication = medicationService.updateMedication(id, updatedDto);

        if(updatedMedication != null){
            return new ResponseEntity<>(updatedMedication, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicationDTO> deleteMedication(@PathVariable Integer id) {
        MedicationDTO deletedMedication = medicationService.deleteMedication(id);
        if (deletedMedication != null) {
            return new ResponseEntity<>(deletedMedication, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
