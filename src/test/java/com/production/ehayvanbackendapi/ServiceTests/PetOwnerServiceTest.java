package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.PetOwnerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetOwnerDTO;
import com.production.ehayvanbackendapi.Entities.Customer;
import com.production.ehayvanbackendapi.Entities.PetOwner;
import com.production.ehayvanbackendapi.Entities.UserType;
import com.production.ehayvanbackendapi.Entities.Veterinarian;
import com.production.ehayvanbackendapi.Mappers.PetOwnerMapper;
import com.production.ehayvanbackendapi.Repositories.PetOwnerRepository;
import com.production.ehayvanbackendapi.Repositories.VeterinarianRepository;
import com.production.ehayvanbackendapi.Services.PetOwnerService;
import com.production.ehayvanbackendapi.TestUtils.DataSeed;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetOwnerServiceTest {

    @SpyBean
    @Autowired
    private PetOwnerService testPetOwnerService;

    @SpyBean
    @Autowired
    private PetOwnerRepository testPetOwnerRepository;

    @SpyBean
    @Autowired
    private VeterinarianRepository testVeterinarianRepository;

    @Autowired
    DataSeed dataSeed;

    private PetOwner testPetOwner;

    private CreateOrUpdatePetOwnerDTO testCreateOrUpdatePetOwnerDTO;

    @Autowired
    private PetOwnerMapper testPetOwnerMapper;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testPetOwner = new PetOwner();
        testPetOwner.setPetOwnerID(0);
        testPetOwner.setUser(new Customer());
        testPetOwner.getUser().setUserTypeID(new UserType(1));
        testPetOwner.getUser().setName("And i return to nothingness");
        testPetOwner.getUser().setSurname("Shore");
        testPetOwner.getUser().setEmail("pain_remains_2@gmail.com");
        testPetOwner.getUser().setPassword("remainsssssss");

        testCreateOrUpdatePetOwnerDTO = new CreateOrUpdatePetOwnerDTO(testPetOwnerMapper.convertToDto(testPetOwner));
    }

    @AfterEach
    public void onEachTestEnd() {
        testCreateOrUpdatePetOwnerDTO = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testPetOwnerId = 0;
        PetOwnerDTO returnedPetOwnerDTO = testPetOwnerService.getPetOwnerById(testPetOwnerId);
        assertThat(returnedPetOwnerDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        PetOwner returnedPetOwner = testPetOwnerRepository.save(testPetOwner);
        PetOwnerDTO returnedPetOwnerDTO = testPetOwnerService.getPetOwnerById(returnedPetOwner.getPetOwnerID());

        assertThat(returnedPetOwnerDTO).isNotNull();
        assertThat(returnedPetOwnerDTO.getPetOwnerID()).isEqualTo(returnedPetOwner.getPetOwnerID());
        assertThat(returnedPetOwnerDTO.getUser().getName()).isEqualTo(returnedPetOwner.getUser().getName());
        assertThat(returnedPetOwnerDTO.getUser().getSurname()).isEqualTo(returnedPetOwner.getUser().getSurname());
        assertThat(returnedPetOwnerDTO.getUser().getEmail()).isEqualTo(returnedPetOwner.getUser().getEmail());
        assertThat(returnedPetOwnerDTO.getUser().getPassword()).isEqualTo(returnedPetOwner.getUser().getPassword());

        testPetOwnerRepository.deleteById(returnedPetOwner.getPetOwnerID());
    }

    @Test
    @Transactional
    public void testServicePostPetOwner() {
        PetOwnerDTO returnedPetOwnerDTO = testPetOwnerService.postPetOwner(testCreateOrUpdatePetOwnerDTO);

        Optional<PetOwner> searchedPetOwnerOptional = testPetOwnerRepository.findById(returnedPetOwnerDTO.getPetOwnerID());
        assertThat(searchedPetOwnerOptional.isPresent()).isEqualTo(true);
        assertThat(searchedPetOwnerOptional.orElseThrow().getPetOwnerID()).isEqualTo(returnedPetOwnerDTO.getPetOwnerID());

        testPetOwnerRepository.deleteById(searchedPetOwnerOptional.orElseThrow().getPetOwnerID());
    }

    @Test
    @Transactional
    public void testServiceUpdatePetOwner() {
        // firstly save new PetOwner
        PetOwner returnedPetOwner = testPetOwnerRepository.save(testPetOwner);

        // convert to DTO and change email address of new created DTO
        testCreateOrUpdatePetOwnerDTO = new CreateOrUpdatePetOwnerDTO(testPetOwnerMapper.convertToDto(testPetOwner));
        testCreateOrUpdatePetOwnerDTO.getUser().setEmail("if_darkness_had_a_son@gmail.com");

        // update corresponding PetOwner
        PetOwnerDTO returnedPetOwnerDTO = testPetOwnerService.updatePetOwner(returnedPetOwner.getPetOwnerID(), testCreateOrUpdatePetOwnerDTO);

        // check if it is same as email that updated data and data we want to update
        Optional<PetOwner> searchedPetOwnerOptional = testPetOwnerRepository.findById(returnedPetOwner.getPetOwnerID());
        assertThat(searchedPetOwnerOptional.isPresent()).isEqualTo(true);
        assertThat(searchedPetOwnerOptional.orElseThrow().getPetOwnerID()).isEqualTo(returnedPetOwnerDTO.getPetOwnerID());
        assertThat(searchedPetOwnerOptional.orElseThrow().getUser().getEmail()).isEqualTo(testCreateOrUpdatePetOwnerDTO.getUser().getEmail());

        // delete added data for testing
        testPetOwnerRepository.deleteById(searchedPetOwnerOptional.orElseThrow().getPetOwnerID());
    }

    @Test
    @Transactional
    public void testServiceUpdateAssignedVeterinarian() {
        testPetOwner.setPetOwnerID(0);
        testPetOwner.setVet(new Veterinarian());
        testPetOwner.getVet().setClinic("sun of the run");
        testPetOwner.setAppointments(List.of());
        PetOwner returnedPetOwner = testPetOwnerRepository.save(testPetOwner);

        Veterinarian otherVeterinarian = new Veterinarian();
        otherVeterinarian.setVetID(0);
        otherVeterinarian.setClinic("goke bakma duragi");
        otherVeterinarian = testVeterinarianRepository.save(otherVeterinarian);

        PetOwnerDTO updatedPetOwnerByVeterinarian = testPetOwnerService.updateAssignedVeterinarian(otherVeterinarian.getVetID(), returnedPetOwner.getPetOwnerID());

        assertThat(updatedPetOwnerByVeterinarian).isNotNull();
        assertThat(updatedPetOwnerByVeterinarian.getVetID()).isEqualTo(otherVeterinarian.getVetID());

        Optional<Veterinarian> newAssignedVeterinarian = testVeterinarianRepository.findById(updatedPetOwnerByVeterinarian.getVetID());
        assertThat(newAssignedVeterinarian.orElseThrow().getClinic()).isEqualTo(otherVeterinarian.getClinic());

        testVeterinarianRepository.deleteById(otherVeterinarian.getVetID());
        testPetOwnerRepository.deleteById(returnedPetOwner.getPetOwnerID());
    }

    @Test
    @Transactional
    public void testServiceDeletePetOwner() {
        // firstly save new PetOwner
        PetOwner returnedPetOwner = testPetOwnerRepository.save(testPetOwner);
        Optional<PetOwner> searchedPetOwner = testPetOwnerRepository.findById(returnedPetOwner.getPetOwnerID());
        assertThat(searchedPetOwner.isPresent()).isEqualTo(true);

        PetOwnerDTO deletedPetOwner = testPetOwnerService.deletePetOwner(returnedPetOwner.getPetOwnerID());

        assertThat(deletedPetOwner.getPetOwnerID()).isEqualTo(returnedPetOwner.getPetOwnerID());

        searchedPetOwner = testPetOwnerRepository.findById(returnedPetOwner.getPetOwnerID());
        assertThat(searchedPetOwner.isPresent()).isEqualTo(false);
    }
}
