package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.CustomerDTO;
import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateMedicationDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.MedicationMapper;
import com.production.ehayvanbackendapi.Repositories.MedicationRepository;
import com.production.ehayvanbackendapi.Services.MedicationService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicationServiceTest {
    @SpyBean
    @Autowired
    private MedicationService testMedicationService;

    @SpyBean
    @Autowired
    private MedicationRepository testMedicationRepository;

    @Autowired
    DataSeed dataSeed;

    private Medication testMedication;

    private CreateOrUpdateMedicationDTO testCreateOrUpdateMedicationDTO;

    @Autowired
    private MedicationMapper testMedicationMapper;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testMedication = new Medication();
        testMedication.setMedicationID(0);
        testMedication.setMedicationName("Akyazi Sifasi");
        testMedication.setPetID(new Pet());
        testMedication.setScheduleID(new Schedule());
        testMedication.getScheduleID().setScheduleID(1);
        testMedication.getScheduleID().setBeginningDate(LocalDate.of(1915, Month.MARCH, 15));
        testMedication.getScheduleID().setDoseFrequency(15);
        testMedication.getScheduleID().setDoseCount(1773);
        testMedication.setMedTypeID(new MedType());
        testMedication.getMedTypeID().setMedTypeID(1);

        testCreateOrUpdateMedicationDTO = new CreateOrUpdateMedicationDTO(testMedicationMapper.convertToDto(testMedication));
    }

    @AfterEach
    public void onEachTestEnd() {
        testCreateOrUpdateMedicationDTO = null;
        testMedication = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testMedicationId = 0;
        MedicationDTO returnedMedicationDTO = testMedicationService.getMedicationById(testMedicationId);
        assertThat(returnedMedicationDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        Medication returnedMedication = testMedicationRepository.save(testMedication);
        MedicationDTO returnedMedicationDTO = testMedicationService.getMedicationById(returnedMedication.getMedicationID());

        assertThat(returnedMedicationDTO).isNotNull();
        assertThat(returnedMedicationDTO.getMedicationID()).isEqualTo(returnedMedication.getMedicationID());
        assertThat(returnedMedicationDTO.getMedicationName()).isEqualTo(returnedMedication.getMedicationName());

        testMedicationRepository.deleteById(returnedMedication.getMedicationID());
    }

    @Test
    @Transactional
    public void testServicePostMedication() {
        MedicationDTO returnedMedicationDTO = testMedicationService.postMedication(testCreateOrUpdateMedicationDTO);

        Optional<Medication> searchedMedicationOptional = testMedicationRepository.findById(returnedMedicationDTO.getMedicationID());
        assertThat(searchedMedicationOptional.isPresent()).isEqualTo(true);
        assertThat(searchedMedicationOptional.orElseThrow().getMedicationID()).isEqualTo(returnedMedicationDTO.getMedicationID());

        testMedicationRepository.deleteById(searchedMedicationOptional.orElseThrow().getMedicationID());
    }

    @Test
    @Transactional
    public void testServiceUpdateMedication() {
        // firstly save new Medication
        Medication returnedMedication = testMedicationRepository.save(testMedication);

        // convert to DTO and change email address of new created DTO
        testCreateOrUpdateMedicationDTO = new CreateOrUpdateMedicationDTO(testMedicationMapper.convertToDto(testMedication));
        testCreateOrUpdateMedicationDTO.setMedicationName("Anaconda");
        testCreateOrUpdateMedicationDTO.getScheduleID().setBeginningDate(LocalDate.of(3000, Month.DECEMBER, 12));

        // update corresponding MedicationOwner
        MedicationDTO returnedMedicationDTO = testMedicationService.updateMedication(returnedMedication.getMedicationID(), testCreateOrUpdateMedicationDTO);

        // check if it is same as email that updated data and data we want to update
        Optional<Medication> searchedMedicationOptional = testMedicationRepository.findById(returnedMedication.getMedicationID());
        assertThat(searchedMedicationOptional.isPresent()).isEqualTo(true);
        assertThat(searchedMedicationOptional.orElseThrow().getMedicationName()).isEqualTo(testCreateOrUpdateMedicationDTO.getMedicationName());

        // delete added data for testing
        testMedicationRepository.deleteById(searchedMedicationOptional.orElseThrow().getMedicationID());
    }

    @Test
    @Transactional
    public void testServiceDeleteMedication() {
        // firstly save new Medication
        Medication returnedMedication = testMedicationRepository.save(testMedication);
        Optional<Medication> searchedMedication = testMedicationRepository.findById(returnedMedication.getMedicationID());
        assertThat(searchedMedication.isPresent()).isEqualTo(true);

        MedicationDTO deletedMedication = testMedicationService.deleteMedication(returnedMedication.getMedicationID());

        assertThat(deletedMedication.getMedicationID()).isEqualTo(returnedMedication.getMedicationID());

        searchedMedication = testMedicationRepository.findById(returnedMedication.getMedicationID());
        assertThat(searchedMedication.isPresent()).isEqualTo(false);
    }

    @Test
    @Transactional
    public void testServiceGetAllMedication() {
        List<Medication> listOfAllAddedMedications = new ArrayList<>();

        testMedication.setMedicationID(0);
        testMedication.setPetID(new Pet());
        testMedication.getPetID().setPetID(1);
        listOfAllAddedMedications.add(testMedicationRepository.save(testMedication));

        testMedication.setMedicationID(0);
        testMedication.setPetID(new Pet());
        testMedication.getPetID().setPetID(1);
        listOfAllAddedMedications.add(testMedicationRepository.save(testMedication));

        testMedication.setMedicationID(0);
        testMedication.setPetID(new Pet());
        testMedication.getPetID().setPetID(1);
        listOfAllAddedMedications.add(testMedicationRepository.save(testMedication));

        List<MedicationDTO> MedicationList = testMedicationService.getAllMedications();

        // we include seeded data before tests start. So we expect that size of returned list
        // is added_items_size + 1. "1" is seeded data in there.
        assertThat(MedicationList.size() - 1).isEqualTo(listOfAllAddedMedications.size());

        // @TODO CihatAltiparmak : Find more chic solution, it's hard coded.
        // check if there is seeded data's user id
        assertThat(MedicationList.stream().map(
                MedicationDTO::getMedicationID).toList().contains(1))
                .isEqualTo(true);

        for (Medication addedMedication : listOfAllAddedMedications) {
            assertThat(MedicationList.stream().map(
                    MedicationDTO::getMedicationID).toList().contains(addedMedication.getMedicationID()))
                    .isEqualTo(true);
        }

        for (Medication addedMedication : listOfAllAddedMedications) {
            testMedicationRepository.deleteById(addedMedication.getMedicationID());
        }
    }
}
