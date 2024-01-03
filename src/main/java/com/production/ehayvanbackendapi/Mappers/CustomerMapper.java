package com.production.ehayvanbackendapi.Mappers;
import com.production.ehayvanbackendapi.DTO.CustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.RegisterCustomerDTO;
import com.production.ehayvanbackendapi.Entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Customer.class, CustomerDTO.class).addMappings(
                mapper -> {
                    mapper.map(src -> src.getVet().getVetID(), CustomerDTO::setVetID);
                    mapper.map(src -> src.getOwner().getPetOwnerID(), CustomerDTO::setOwnerID);
                    mapper.map(src -> src.getUserTypeID().getUserTypeID(), CustomerDTO::setUserTypeID);
                }
        );
        this.modelMapper.createTypeMap(CreateOrUpdateCustomerDTO.class, Customer.class).addMappings(
                mapper -> {
                    mapper.skip(Customer::setOwner);
                }
        );
        this.modelMapper.createTypeMap(RegisterCustomerDTO.class, Customer.class).addMappings(
                mapper -> {
                    mapper.skip(Customer::setOwner);
                }
        );
    }

    public CustomerDTO convertToDto(Customer customer) {
        return  modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
    public Customer convertToEntity2(RegisterCustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
