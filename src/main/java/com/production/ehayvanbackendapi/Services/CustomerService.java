package com.production.ehayvanbackendapi.Services;

import com.production.ehayvanbackendapi.DTO.CustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.RegisterCustomerDTO;
import com.production.ehayvanbackendapi.Entities.Customer;
import com.production.ehayvanbackendapi.Mappers.CustomerMapper;
import com.production.ehayvanbackendapi.Repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return customer != null ? customerMapper.convertToDto(customer) : null;
    }

    public CustomerDTO customerLogin(String email, String password){
        Optional<Customer> targetCustomer = customerRepository.findCustomerByEmailAndPassword(email, password);
        return targetCustomer.map(customerMapper::convertToDto).orElse(null);
    }

//    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
//        Customer customer = customerMapper.convertToEntity(customerDTO);
//        Customer savedCustomer = customerRepository.save(customer);
//        return customerMapper.convertToDto(savedCustomer);
//    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDtoList = new ArrayList<>();
        for(Customer cust: customerList){
            customerDtoList.add(customerMapper.convertToDto(cust));
        }
        return customerDtoList;
    }

    public CustomerDTO registerCustomer(RegisterCustomerDTO customerDTO) {
        // Check if the customer with the given email already exists
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerDTO.getEmail());

        if (existingCustomer.isPresent()) {
            // Customer with the same email already exists
            return null;
        } else {
            // Customer does not exist, proceed with registration
            Customer customer = customerMapper.convertToEntity2(customerDTO);
            Customer savedCustomer = customerRepository.save(customer);
            return customerMapper.convertToDto(savedCustomer);
        }
    }

    public CustomerDTO deleteCustomer(Integer id){
        // Find the target customer to delete.
        Optional<Customer> targetCustomer = customerRepository.findById(id);

        // Delete customer and return dto if it exists.
        if(targetCustomer.isPresent()){
            customerRepository.delete(targetCustomer.get());
            return customerMapper.convertToDto(targetCustomer.get());
        }
        // Return null if target entity does not exist.
        return null;
    }

    // Other service methods for creating, updating, and deleting customers
}

