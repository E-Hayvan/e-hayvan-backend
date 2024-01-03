package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.CustomerDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.RegisterCustomerDTO;
import com.production.ehayvanbackendapi.Services.CustomerService;
import com.production.ehayvanbackendapi.Services.VeterinarianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final VeterinarianService veterinarianService;


    public CustomerController(CustomerService customerService, VeterinarianService veterinarianService) {
        this.customerService = customerService;
        this.veterinarianService = veterinarianService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);

        if (customerDTO != null) {
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        List<CustomerDTO> response = customerService.getAllCustomers();

        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody RegisterCustomerDTO customerDTO) {
        CustomerDTO registeredCustomer = customerService.registerCustomer(customerDTO);

        if (registeredCustomer != null) {
            return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
        } else {
            // Customer with the same email already exists
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<CustomerDTO> getCustomerLogin(@PathVariable String email,
                                                        @PathVariable String password){
        CustomerDTO response = customerService.customerLogin(email, password);
        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/loginAdmin/{email}/{password}")
//    public ResponseEntity<CustomerDTO> AdminLogin(@PathVariable String email,
//                                                        @PathVariable String password){
//        CustomerDTO response = customerService.customerLogin(email, password);
//        if(response != null&& Objects.equals(email, "admin@admin.com")){
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/loginAdmin/{email}/{password}")
    public String getAdminLogin(@PathVariable String email,
                                                  @PathVariable String password, Model model){
        CustomerDTO response = customerService.customerLogin(email, password);
        if(response != null&& Objects.equals(email, "admin@admin.com")){
            List<VeterinarianDTO> veterinarians = veterinarianService.getAllVeterinarians();
            model.addAttribute("veterinarians", veterinarians);
            model.addAttribute("viewType", "default");
            return "redirect:/api/veterinarians/desktop";
        } else {
            return "redirect:/api/customers/login";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable Integer id){
        CustomerDTO deletedCustomer = customerService.deleteCustomer(id);
        if(deletedCustomer != null){
            return new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    @PostMapping
//    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
//        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
//        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
//    }

    // Other controller methods for creating, updating, and deleting customers
}

