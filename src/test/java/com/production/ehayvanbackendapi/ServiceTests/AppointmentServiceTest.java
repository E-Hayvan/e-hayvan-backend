package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.AppointmentDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateAppointmentDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.AppointmentMapper;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Repositories.AppointmentRepository;
import com.production.ehayvanbackendapi.Repositories.PetOwnerRepository;
import com.production.ehayvanbackendapi.Repositories.PetRepository;
import com.production.ehayvanbackendapi.Repositories.VeterinarianRepository;
import com.production.ehayvanbackendapi.Services.AppointmentService;
import com.production.ehayvanbackendapi.Services.PetService;
import com.production.ehayvanbackendapi.Services.VeterinarianService;
import com.production.ehayvanbackendapi.TestUtils.DataSeed;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppointmentServiceTest {
    @SpyBean
    @Autowired
    private AppointmentService testAppointmentService;

    @SpyBean
    @Autowired
    private AppointmentRepository testAppointmentRepository;

    @SpyBean
    @Autowired
    private PetOwnerRepository testPetOwnerRepository;

    @SpyBean
    @Autowired
    private VeterinarianRepository testVeterinarianRepository;

    @Autowired
    DataSeed dataSeed;

    private Appointment testAppointment;

    private CreateOrUpdateAppointmentDTO testCreateOrUpdateAppointmentDTO;

    @Autowired
    private AppointmentMapper testAppointmentMapper;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testAppointment = new Appointment();
        testAppointment.setAppointmentID(0);
        testAppointment.setPetOwnerID(new PetOwner());
        testAppointment.setPetID(new Pet());
        testAppointment.setVetID(new Veterinarian());
        testAppointment.getPetOwnerID().setPetOwnerID(1);
        testAppointment.getPetID().setPetID(1);
        testAppointment.getVetID().setVetID(1);
        testAppointment.getVetID().setAppointments(List.of());
        testAppointment.setAppointmentDate(LocalDateTime.of(2049, Month.APRIL, 21, 1, 1));

        testCreateOrUpdateAppointmentDTO = new CreateOrUpdateAppointmentDTO(testAppointmentMapper.convertToDto(testAppointment));
    }

    @AfterEach
    public void onEachTestEnd() {
        testCreateOrUpdateAppointmentDTO = null;
        testAppointment = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testAppointmentId = 0;
        AppointmentDTO returnedPetDTO = testAppointmentService.getAppointmentById(testAppointmentId);
        assertThat(returnedPetDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        Appointment returnedAppointment = testAppointmentRepository.save(testAppointment);
        AppointmentDTO returnedAppointmentDTO = testAppointmentService.getAppointmentById(returnedAppointment.getAppointmentID());

        assertThat(returnedAppointmentDTO).isNotNull();
        assertThat(returnedAppointmentDTO.getAppointmentID()).isEqualTo(returnedAppointment.getAppointmentID());
        assertThat(returnedAppointmentDTO.getPetOwnerID()).isEqualTo(returnedAppointment.getPetOwnerID().getPetOwnerID());
        assertThat(returnedAppointmentDTO.getAppointmentDate()).isEqualTo(returnedAppointment.getAppointmentDate());

        testAppointmentRepository.deleteById(returnedAppointment.getAppointmentID());
    }

    @Test
    @Transactional
    public void testServicePostAppointment() {
        AppointmentDTO returnedAppointmentDTO = testAppointmentService.postAppointment(testCreateOrUpdateAppointmentDTO);

        Optional<Appointment> searchedAppointmentOptional = testAppointmentRepository.findById(returnedAppointmentDTO.getAppointmentID());
        assertThat(searchedAppointmentOptional.isPresent()).isEqualTo(true);
        assertThat(searchedAppointmentOptional.orElseThrow().getPetID().getPetID()).isEqualTo(returnedAppointmentDTO.getPetID());

        testAppointmentRepository.deleteById(searchedAppointmentOptional.orElseThrow().getAppointmentID());
    }

    @Test
    @Transactional
    public void testServiceUpdateAppointment() {
        // firstly save new Appointment
        Appointment returnedAppointment = testAppointmentRepository.save(testAppointment);

        // convert to DTO and change email address of new created DTO
        testCreateOrUpdateAppointmentDTO = new CreateOrUpdateAppointmentDTO(testAppointmentMapper.convertToDto(testAppointment));
        testCreateOrUpdateAppointmentDTO.setAppointmentDate(LocalDateTime.of(1945, Month.SEPTEMBER, 2, 1, 1));

        // update corresponding Appointment
        AppointmentDTO returnedAppointmentDTO = testAppointmentService.updateAppointment(returnedAppointment.getAppointmentID(), testCreateOrUpdateAppointmentDTO);

        // check if it is same as email that updated data and data we want to update
        Optional<Appointment> searchedAppointmentOptional = testAppointmentRepository.findById(returnedAppointment.getAppointmentID());
        assertThat(searchedAppointmentOptional.isPresent()).isEqualTo(true);
        assertThat(searchedAppointmentOptional.orElseThrow().getAppointmentDate()).isEqualTo(returnedAppointmentDTO.getAppointmentDate());

        // delete added data for testing
        testAppointmentRepository.deleteById(searchedAppointmentOptional.orElseThrow().getAppointmentID());
    }

    @Test
    @Transactional
    public void testServiceDeleteAppointment() {
        // firstly save new Appointment
        Appointment returnedAppointment = testAppointmentRepository.save(testAppointment);
        Optional<Appointment> searchedAppointment = testAppointmentRepository.findById(returnedAppointment.getAppointmentID());
        assertThat(searchedAppointment.isPresent()).isEqualTo(true);

        AppointmentDTO deletedAppointment = testAppointmentService.deleteAppointment(returnedAppointment.getAppointmentID());

        assertThat(deletedAppointment.getAppointmentDate()).isEqualTo(returnedAppointment.getAppointmentDate());

        searchedAppointment = testAppointmentRepository.findById(returnedAppointment.getAppointmentID());
        assertThat(searchedAppointment.isPresent()).isEqualTo(false);
    }

    @Test
    @Transactional
    public void testServiceGetAllAppointments() {
        List<Appointment> listOfAllAddedAppointments = new ArrayList<>();
        Appointment returnedAppointment;

        testAppointment.setAppointmentID(0);
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        testAppointment.setAppointmentID(0);
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        testAppointment.setAppointmentID(0);
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        List<AppointmentDTO> appointmentList = testAppointmentService.getAllAppointments();

        // we include seeded data before tests start. So we expect that size of returned list
        // is added_items_size + 1. "1" is seeded data in there.
        assertThat(appointmentList.size() - 1).isEqualTo(listOfAllAddedAppointments.size());

        // check if there is seeded data's appointment id
        assertThat(appointmentList.stream().map(
                AppointmentDTO::getAppointmentID).toList().contains(1))
                .isEqualTo(true);
        for (Appointment addedAppointment : listOfAllAddedAppointments) {
            assertThat(appointmentList.stream().map(
                    AppointmentDTO::getAppointmentID).toList().contains(addedAppointment.getAppointmentID()))
                    .isEqualTo(true);
        }

        for (Appointment addedAppointment : listOfAllAddedAppointments) {
            testAppointmentRepository.deleteById(addedAppointment.getAppointmentID());
        }
    }

    @Test
    @Transactional
    public void testServiceGetAllAppointmentsForPetOwner() {
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

        List<Appointment> listOfAllAddedAppointmentsForPetOwner = new ArrayList<>();
        List<Appointment> listOfAllAddedAppointments = new ArrayList<>();
        Appointment returnedAppointment;

        testAppointment.setAppointmentID(0);
        testAppointment.getPetOwnerID().setPetOwnerID(listOfAllAddedPetOwner.get(0).getPetOwnerID());
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointmentsForPetOwner.add(returnedAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        testAppointment.setAppointmentID(0);
        testAppointment.getPetOwnerID().setPetOwnerID(listOfAllAddedPetOwner.get(0).getPetOwnerID());
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointmentsForPetOwner.addLast(returnedAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        testAppointment.setAppointmentID(0);
        testAppointment.getPetOwnerID().setPetOwnerID(listOfAllAddedPetOwner.get(1).getPetOwnerID());
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        // listOfAllAddedAppointmentsForPetOwner.addLast(returnedAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        Integer testPetOwnerId = listOfAllAddedPetOwner.get(0).getPetOwnerID();
        List<AppointmentDTO> appointmentList = testAppointmentService.getAllAppointmentsForPetOwner(testPetOwnerId);

        // we exlude seeded data before tests start. Because pet owner id of seededAppointment data
        // is not inside list of new added pet owner id.
        assertThat(appointmentList.size()).isEqualTo(listOfAllAddedAppointmentsForPetOwner.size());

        // check if there is not seeded data's appointment id
        assertThat(appointmentList.stream().map(
                AppointmentDTO::getAppointmentID).toList().contains(1))
                .isEqualTo(false);
        for (Appointment addedAppointment : listOfAllAddedAppointmentsForPetOwner) {
            assertThat(appointmentList.stream().map(
                    AppointmentDTO::getAppointmentID).toList().contains(addedAppointment.getAppointmentID()))
                    .isEqualTo(true);
        }

        for (Appointment addedAppointment : listOfAllAddedAppointments) {
            testAppointmentRepository.deleteById(addedAppointment.getAppointmentID());
        }

        for (PetOwner addedPetOwner : listOfAllAddedPetOwner) {
            testPetOwnerRepository.deleteById(addedPetOwner.getPetOwnerID());
        }
    }

    @Test
    @Transactional
    public void testServiceGetAllAppointmentsForVeterinarian() {
        List<Veterinarian> listOfAllAddedVeterinarian = new ArrayList<>();

        Veterinarian otherVeterinarian = new Veterinarian();
        otherVeterinarian.setVetID(0);
        otherVeterinarian.setClinic("cincinella");
        otherVeterinarian.setUser(new Customer());
        otherVeterinarian.getUser().setUserTypeID(new UserType(2));
        otherVeterinarian.getUser().setName("Yakisikli");
        otherVeterinarian.getUser().setSurname("Guvenlik");
        otherVeterinarian.getUser().setEmail("Stella@Mtella.com");
        otherVeterinarian.getUser().setPassword("akaryakit");
        listOfAllAddedVeterinarian.add(testVeterinarianRepository.save(otherVeterinarian));

        otherVeterinarian.setVetID(0);
        otherVeterinarian.setClinic("Medico");
        otherVeterinarian.setUser(new Customer());
        otherVeterinarian.getUser().setUserTypeID(new UserType(2));
        otherVeterinarian.getUser().setName("Erkan");
        otherVeterinarian.getUser().setSurname("Abe");
        otherVeterinarian.getUser().setEmail("kamiller@gmail.com");
        otherVeterinarian.getUser().setPassword("dogalgaz");
        listOfAllAddedVeterinarian.add(testVeterinarianRepository.save(otherVeterinarian));

        List<Appointment> listOfAllAddedAppointmentsForVeterinarian = new ArrayList<>();
        List<Appointment> listOfAllAddedAppointments = new ArrayList<>();
        Appointment returnedAppointment;

        testAppointment.setAppointmentID(0);
        testAppointment.getVetID().setVetID(listOfAllAddedVeterinarian.get(0).getVetID());
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointmentsForVeterinarian.add(returnedAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        testAppointment.setAppointmentID(0);
        testAppointment.getVetID().setVetID(listOfAllAddedVeterinarian.get(0).getVetID());
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        listOfAllAddedAppointmentsForVeterinarian.addLast(returnedAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        testAppointment.setAppointmentID(0);
        testAppointment.getVetID().setVetID(listOfAllAddedVeterinarian.get(1).getVetID());
        returnedAppointment = testAppointmentRepository.save(testAppointment);
        // listOfAllAddedAppointmentsForVeterinarian.addLast(returnedAppointment);
        listOfAllAddedAppointments.add(returnedAppointment);

        Integer testVeterinarianId = listOfAllAddedVeterinarian.get(0).getVetID();
        List<AppointmentDTO> appointmentList = testAppointmentService.getAllAppointmentsForVeterinarian(testVeterinarianId);

        // we exlude seeded data before tests start. Because pet owner id of seededAppointment data
        // is not inside list of new added pet owner id.
        assertThat(appointmentList.size()).isEqualTo(listOfAllAddedAppointmentsForVeterinarian.size());

        // check if there is not seeded data's appointment id
        assertThat(appointmentList.stream().map(
                AppointmentDTO::getAppointmentID).toList().contains(1))
                .isEqualTo(false);
        for (Appointment addedAppointment : listOfAllAddedAppointmentsForVeterinarian) {
            assertThat(appointmentList.stream().map(
                    AppointmentDTO::getAppointmentID).toList().contains(addedAppointment.getAppointmentID()))
                    .isEqualTo(true);
        }

        for (Appointment addedAppointment : listOfAllAddedAppointments) {
            testAppointmentRepository.deleteById(addedAppointment.getAppointmentID());
        }

        for (Veterinarian addedVeterinarian : listOfAllAddedVeterinarian) {
            testVeterinarianRepository.deleteById(addedVeterinarian.getVetID());
        }
    }
}
