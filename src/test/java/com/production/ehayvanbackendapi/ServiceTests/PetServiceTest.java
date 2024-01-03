package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Repositories.PetOwnerRepository;
import com.production.ehayvanbackendapi.Repositories.PetRepository;
import com.production.ehayvanbackendapi.Services.PetService;
import com.production.ehayvanbackendapi.TestUtils.DataSeed;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetServiceTest {
    @SpyBean
    @Autowired
    private PetService testPetService;

    @SpyBean
    @Autowired
    private PetRepository testPetRepository;

    @SpyBean
    @Autowired
    private PetOwnerRepository testPetOwnerRepository;

    @Autowired
    DataSeed dataSeed;

    private Pet testPet;

    private CreateOrUpdatePetDTO testCreateOrUpdatePetDTO;

    @Autowired
    private PetMapper testPetMapper;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testPet = new Pet();
        testPet.setPetID(0);
        testPet.setPetName("Ingiliz Ati");
        testPet.setPetTypeID(new PetType());
        testPet.getPetTypeID().setPetTypeID(1);
        testPet.setAge(12);
        testPet.setPetOwnerID(new PetOwner());
        testPet.getPetOwnerID().setUser(new Customer());
        testPet.getPetOwnerID().getUser().setUserTypeID(new UserType(1));
        testPet.getPetOwnerID().getUser().setName("And i return to nothingness");
        testPet.getPetOwnerID().getUser().setSurname("Shore");
        testPet.getPetOwnerID().getUser().setEmail("pain_remains_2@gmail.com");
        testPet.getPetOwnerID().getUser().setPassword("remainsssssss");

        testCreateOrUpdatePetDTO = new CreateOrUpdatePetDTO(testPetMapper.convertToDto(testPet));
    }

    @AfterEach
    public void onEachTestEnd() {
        testCreateOrUpdatePetDTO = null;
        testPet = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testPetId = 0;
        PetDTO returnedPetDTO = testPetService.getPetById(testPetId);
        assertThat(returnedPetDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        Pet returnedPet = testPetRepository.save(testPet);
        PetDTO returnedPetDTO = testPetService.getPetById(returnedPet.getPetID());

        assertThat(returnedPetDTO).isNotNull();
        assertThat(returnedPetDTO.getPetID()).isEqualTo(returnedPet.getPetID());
        assertThat(returnedPetDTO.getPetOwnerID()).isEqualTo(returnedPet.getPetOwnerID().getPetOwnerID());
        assertThat(returnedPetDTO.getPetName()).isEqualTo(returnedPet.getPetName());

        testPetRepository.deleteById(returnedPet.getPetID());
    }

    @Test
    @Transactional
    public void testServicePostPet() {
        PetDTO returnedPetDTO = testPetService.postPet(testCreateOrUpdatePetDTO);

        Optional<Pet> searchedPetOptional = testPetRepository.findById(returnedPetDTO.getPetID());
        assertThat(searchedPetOptional.isPresent()).isEqualTo(true);
        assertThat(searchedPetOptional.orElseThrow().getPetID()).isEqualTo(returnedPetDTO.getPetID());

        testPetRepository.deleteById(searchedPetOptional.orElseThrow().getPetID());
    }

    @Test
    @Transactional
    public void testServiceUpdatePet() {
        // firstly save new Pet
        Pet returnedPet = testPetRepository.save(testPet);

        // convert to DTO and change email address of new created DTO
        testCreateOrUpdatePetDTO = new CreateOrUpdatePetDTO(testPetMapper.convertToDto(testPet));
        testCreateOrUpdatePetDTO.setPetName("Anaconda");
        testCreateOrUpdatePetDTO.setPetTypeID(1);

        // update corresponding PetOwner
        PetDTO returnedPetDTO = testPetService.updatePet(returnedPet.getPetID(), testCreateOrUpdatePetDTO);

        // check if it is same as email that updated data and data we want to update
        Optional<Pet> searchedPetOptional = testPetRepository.findById(returnedPet.getPetID());
        assertThat(searchedPetOptional.isPresent()).isEqualTo(true);
        assertThat(searchedPetOptional.orElseThrow().getPetOwnerID().getPetOwnerID()).isEqualTo(returnedPetDTO.getPetOwnerID());
        assertThat(searchedPetOptional.orElseThrow().getPetName()).isEqualTo(testCreateOrUpdatePetDTO.getPetName());
        assertThat(searchedPetOptional.orElseThrow().getPetTypeID().getPetTypeID()).isEqualTo(testCreateOrUpdatePetDTO.getPetTypeID());

        // delete added data for testing
        testPetRepository.deleteById(searchedPetOptional.orElseThrow().getPetID());
    }

    @Test
    @Transactional
    public void testServiceDeletePet() {
        // firstly save new Pet
        Pet returnedPet = testPetRepository.save(testPet);
        Optional<Pet> searchedPet = testPetRepository.findById(returnedPet.getPetID());
        assertThat(searchedPet.isPresent()).isEqualTo(true);

        PetDTO deletedPet = testPetService.deletePet(returnedPet.getPetID());

        assertThat(deletedPet.getPetID()).isEqualTo(returnedPet.getPetID());

        searchedPet = testPetRepository.findById(returnedPet.getPetID());
        assertThat(searchedPet.isPresent()).isEqualTo(false);
    }

    @Test
    @Transactional
    public void testServiceGetAllPets() {
        List<Pet> listOfAllAddedPets = new ArrayList<>();
        Pet returnedPet;

        testPet.setPetID(0);
        testPet.setPetOwnerID(new PetOwner());
        testPet.getPetOwnerID().setPetOwnerID(1);
        returnedPet = testPetRepository.save(testPet);
        listOfAllAddedPets.add(returnedPet);

        testPet.setPetID(0);
        testPet.setPetOwnerID(new PetOwner());
        testPet.getPetOwnerID().setPetOwnerID(1);
        returnedPet = testPetRepository.save(testPet);
        listOfAllAddedPets.add(returnedPet);

        testPet.setPetID(0);
        testPet.setPetOwnerID(new PetOwner());
        testPet.getPetOwnerID().setPetOwnerID(1);
        returnedPet = testPetRepository.save(testPet);
        listOfAllAddedPets.add(returnedPet);

        List<PetDTO> petList = testPetService.getAllPets();

        // we include seeded data before tests start. So we expect that size of returned list
        // is added_items_size + 1. "1" is seeded data in there.
        assertThat(petList.size() - 1).isEqualTo(listOfAllAddedPets.size());

        // check if there is seeded data's Pet id
        assertThat(petList.stream().map(
                PetDTO::getPetID).toList().contains(1))
                .isEqualTo(true);
        for (Pet addedPet : listOfAllAddedPets) {
            assertThat(petList.stream().map(
                    PetDTO::getPetID).toList().contains(addedPet.getPetID()))
                    .isEqualTo(true);
        }

        for (Pet addedPet : listOfAllAddedPets) {
            testPetRepository.deleteById(addedPet.getPetID());
        }
    }

    @Test
    @Transactional
    public void testServiceGetAllPetsForPetOwner() {
        List<PetOwner> listOfAllAddedPetOwner = new ArrayList<>();

        PetOwner otherPetOwner = new PetOwner();
        otherPetOwner.setPetOwnerID(0);
        otherPetOwner.setUser(new Customer());
        otherPetOwner.getUser().setEmail("damacana@gmail.com");
        otherPetOwner.getUser().setPassword("Sopa");
        otherPetOwner.getUser().setSurname("Geyik");
        otherPetOwner.getUser().setName("Erdem");
        listOfAllAddedPetOwner.add(testPetOwnerRepository.save(otherPetOwner));

        otherPetOwner.setPetOwnerID(0);
        otherPetOwner.setUser(new Customer());
        otherPetOwner.getUser().setEmail("topcuyum@gmail.com");
        otherPetOwner.getUser().setPassword("Gec bile kaldim");
        otherPetOwner.getUser().setSurname("Maden");
        otherPetOwner.getUser().setName("Muzaffer");
        listOfAllAddedPetOwner.add(testPetOwnerRepository.save(otherPetOwner));

        List<Pet> listOfAllAddedPetsForPetOwner = new ArrayList<>();
        List<Pet> listOfAllAddedPets = new ArrayList<>();
        Pet returnedPet;

        testPet.setPetID(0);
        testPet.getPetOwnerID().setPetOwnerID(listOfAllAddedPetOwner.get(0).getPetOwnerID());
        returnedPet = testPetRepository.save(testPet);
        listOfAllAddedPetsForPetOwner.add(returnedPet);
        listOfAllAddedPets.add(returnedPet);

        testPet.setPetID(0);
        testPet.getPetOwnerID().setPetOwnerID(listOfAllAddedPetOwner.get(0).getPetOwnerID());
        returnedPet = testPetRepository.save(testPet);
        listOfAllAddedPetsForPetOwner.addLast(returnedPet);
        listOfAllAddedPets.add(returnedPet);

        testPet.setPetID(0);
        testPet.getPetOwnerID().setPetOwnerID(listOfAllAddedPetOwner.get(1).getPetOwnerID());
        returnedPet = testPetRepository.save(testPet);
        // listOfAllAddedPetsForPetOwner.addLast(returnedPet);
        listOfAllAddedPets.add(returnedPet);

        Integer testPetOwnerId = listOfAllAddedPetOwner.get(0).getPetOwnerID();
        List<PetDTO> PetList = testPetService.getAllPetsForPetOwner(testPetOwnerId);

        // we exlude seeded data before tests start. Because pet owner id of seededPet data
        // is not inside list of new added pet owner id.
        assertThat(PetList.size()).isEqualTo(listOfAllAddedPetsForPetOwner.size());

        // check if there is not seeded data's Pet id
        assertThat(PetList.stream().map(
                PetDTO::getPetID).toList().contains(1))
                .isEqualTo(false);
        for (Pet addedPet : listOfAllAddedPetsForPetOwner) {
            assertThat(PetList.stream().map(
                    PetDTO::getPetID).toList().contains(addedPet.getPetID()))
                    .isEqualTo(true);
        }

        for (Pet addedPet : listOfAllAddedPets) {
            testPetRepository.deleteById(addedPet.getPetID());
        }

        for (PetOwner addedPetOwner : listOfAllAddedPetOwner) {
            testPetOwnerRepository.deleteById(addedPetOwner.getPetOwnerID());
        }
    }
}
