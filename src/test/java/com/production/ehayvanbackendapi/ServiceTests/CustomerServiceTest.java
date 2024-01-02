package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.CustomerDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.ScheduleDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateScheduleDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.CustomerMapper;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Mappers.ScheduleMapper;
import com.production.ehayvanbackendapi.Repositories.CustomerRepository;
import com.production.ehayvanbackendapi.Repositories.PetRepository;
import com.production.ehayvanbackendapi.Repositories.ScheduleRepository;
import com.production.ehayvanbackendapi.Services.CustomerService;
import com.production.ehayvanbackendapi.Services.PetService;
import com.production.ehayvanbackendapi.Services.ScheduleService;
import com.production.ehayvanbackendapi.TestUtils.DataSeed;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {
    @SpyBean
    @Autowired
    private CustomerService testCustomerService;

    @SpyBean
    @Autowired
    private CustomerRepository testCustomerRepository;

    @Autowired
    DataSeed dataSeed;

    private Customer testCustomer;

    private CreateOrUpdateCustomerDTO testCreateOrUpdateCustomerDTO;

    @Autowired
    private CustomerMapper testCustomerMapper;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testCustomer = new Customer();
        testCustomer.setUserID(1);
        testCustomer.setName("Meth");
        testCustomer.setSurname("Fulled");
        testCustomer.setPassword("Finn");
        testCustomer.setEmail("finnn@minnn.com");

        testCreateOrUpdateCustomerDTO = new CreateOrUpdateCustomerDTO(testCustomerMapper.convertToDto(testCustomer));
    }

    @AfterEach
    public void onEachTestEnd() {
        testCreateOrUpdateCustomerDTO = null;
        testCustomer = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testCustomerId = 0;
        CustomerDTO returnedCustomerDTO = testCustomerService.getCustomerById(testCustomerId);
        assertThat(returnedCustomerDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        Customer returnedCustomer = testCustomerRepository.save(testCustomer);
        CustomerDTO returnedCustomerDTO = testCustomerService.getCustomerById(returnedCustomer.getUserID());

        assertThat(returnedCustomerDTO).isNotNull();
        assertThat(returnedCustomerDTO.getUserID()).isEqualTo(returnedCustomer.getUserID());
        assertThat(returnedCustomerDTO.getName()).isEqualTo(returnedCustomer.getName());
        assertThat(returnedCustomerDTO.getSurname()).isEqualTo(returnedCustomer.getSurname());
        assertThat(returnedCustomerDTO.getPassword()).isEqualTo(returnedCustomer.getPassword());
        assertThat(returnedCustomerDTO.getEmail()).isEqualTo(returnedCustomer.getEmail());

        testCustomerRepository.deleteById(returnedCustomer.getUserID());
    }

    @Test
    @Transactional
    public void testServiceDeleteCustomer() {
        // firstly save new Customer
        Customer returnedCustomer = testCustomerRepository.save(testCustomer);
        Optional<Customer> searchedCustomer = testCustomerRepository.findById(returnedCustomer.getUserID());
        assertThat(searchedCustomer.isPresent()).isEqualTo(true);

        CustomerDTO deletedCustomer = testCustomerService.deleteCustomer(returnedCustomer.getUserID());

        assertThat(deletedCustomer.getUserID()).isEqualTo(returnedCustomer.getUserID());

        searchedCustomer = testCustomerRepository.findById(returnedCustomer.getUserID());
        assertThat(searchedCustomer.isPresent()).isEqualTo(false);
    }
}
