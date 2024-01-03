package com.production.ehayvanbackendapi.Controllers;
import com.production.ehayvanbackendapi.Services.PetOwnerService;
import com.production.ehayvanbackendapi.Services.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateVeterinarianDTO;
import com.production.ehayvanbackendapi.Services.VeterinarianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/api/veterinarians")
public class VeterinarianController {
    private final VeterinarianService veterinarianService;

    private final PetService petService;
    private final PetOwnerService petOwnerService;


    public VeterinarianController(VeterinarianService veterinarianService, PetService petService, PetOwnerService petOwnerService) {
        this.veterinarianService = veterinarianService;
        this.petService = petService;
        this.petOwnerService = petOwnerService;
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getAllVetsCount() {
        int count = veterinarianService.getAllVetsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianDTO> getVeterinarianById(@PathVariable Integer id) {
        VeterinarianDTO veterinarianDTO = veterinarianService.getVeterinarianById(id);

        if (veterinarianDTO != null) {
            return new ResponseEntity<>(veterinarianDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<VeterinarianDTO>> getVeterinariansByName(@PathVariable String name){
        List<VeterinarianDTO> veterinarianDTOS = veterinarianService.getVeterinariansByClinic(name);

        if(veterinarianDTOS != null){
            return new ResponseEntity<>(veterinarianDTOS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<VeterinarianDTO> saveVeterinarian(@RequestBody CreateOrUpdateVeterinarianDTO veterinarianDTO) {
        VeterinarianDTO savedVeterinarian = veterinarianService.postVeterinarian(veterinarianDTO);

        if(savedVeterinarian != null){
            return new ResponseEntity<>(savedVeterinarian, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VeterinarianDTO> updateVeterinarian(@PathVariable Integer id,
                                                              @RequestBody CreateOrUpdateVeterinarianDTO veterinarianDTO){
        VeterinarianDTO updatedVeterinarian = veterinarianService.updateVeterinarian(id, veterinarianDTO);

        if(updatedVeterinarian != null){
            return new ResponseEntity<>(updatedVeterinarian, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VeterinarianDTO> deleteVeterinarian(@PathVariable Integer id){
        VeterinarianDTO deletedVeterinarian = veterinarianService.deleteVeterinarian(id);

        if(deletedVeterinarian != null){
            return new ResponseEntity<>(deletedVeterinarian, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String getDeleteVeterinarian(@PathVariable Integer id, Model model) {
        try{
            VeterinarianDTO deletedVeterinarian = veterinarianService.deleteVeterinarian(id);
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            List<VeterinarianDTO> veterinarians = veterinarianService.getAllVeterinarians();
            model.addAttribute("veterinarians", veterinarians);
            model.addAttribute("viewType", "default");
            return "redirect:/api/veterinarians/desktop";
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<VeterinarianDTO>> getAllVeterinarians() {
        List<VeterinarianDTO> response = veterinarianService.getAllVeterinarians();

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/desktop")
    public String desktop(Model model) {
        List<VeterinarianDTO> veterinarians = veterinarianService.getAllVeterinarians();
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("viewType", "default");
        Integer vetCount = veterinarianService.getAllVetsCount();
        Integer petCount = petService.getAllPetsCount();
        Integer petOwnerCount = petOwnerService.getAllPetOwnersCount();
        model.addAttribute("vetCount", vetCount);
        model.addAttribute("petCount", petCount);
        model.addAttribute("petOwnerCount", petOwnerCount);
        return "desktop";
    }

    @PostMapping("/desktop")
    public String desktopFormSubmit(@RequestParam String clinicName, RedirectAttributes redirectAttributes) {
        // Redirect to the URL with the user-provided clinicName
        redirectAttributes.addAttribute("name", clinicName);
        return "redirect:/api/veterinarians/desktop/{name}";
    }

    @GetMapping("/desktop/{name}")
    public String desktop(@PathVariable String name, Model model) {
        List<VeterinarianDTO> filteredVeterinarians = veterinarianService.getVeterinariansByClinic(name);

        Integer vetCount = veterinarianService.getAllVetsCount();
        Integer petCount = petService.getAllPetsCount();
        Integer petOwnerCount = petOwnerService.getAllPetOwnersCount();
        model.addAttribute("vetCount", vetCount);
        model.addAttribute("petCount", petCount);
        model.addAttribute("petOwnerCount", petOwnerCount);

        if (filteredVeterinarians == null || filteredVeterinarians.isEmpty()) {
            // Handle the case where no veterinarians are found
            model.addAttribute("viewType", "noResults");
            return "desktop";
        }

        model.addAttribute("filteredVeterinarians", filteredVeterinarians);
        model.addAttribute("viewType", "filtered");
        return "desktop";
    }


    // Other controller methods for updating, deleting veterinarians, etc.
}

