package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.PetTypeDTO;
import com.production.ehayvanbackendapi.DTO.ScheduleDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateScheduleDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Mappers.ScheduleMapper;
import com.production.ehayvanbackendapi.Repositories.PetRepository;
import com.production.ehayvanbackendapi.Repositories.ScheduleRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScheduleServiceTest {
    @SpyBean
    @Autowired
    private ScheduleService testScheduleService;

    @SpyBean
    @Autowired
    private ScheduleRepository testScheduleRepository;

    @Autowired
    DataSeed dataSeed;

    private Schedule testSchedule;

    private CreateOrUpdateScheduleDTO testCreateOrUpdateScheduleDTO;

    @Autowired
    private ScheduleMapper testScheduleMapper;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testSchedule = new Schedule();
        testSchedule.setScheduleID(0);
        testSchedule.setDoseCount(12);
        testSchedule.setDoseFrequency(1000);
        testSchedule.setBeginningDate(LocalDate.of(1995, Month.APRIL, 30));

        testCreateOrUpdateScheduleDTO = new CreateOrUpdateScheduleDTO(testScheduleMapper.convertToDto(testSchedule));
    }

    @AfterEach
    public void onEachTestEnd() {
        testCreateOrUpdateScheduleDTO = null;
        testSchedule = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testScheduleId = 0;
        ScheduleDTO returnedScheduleDTO = testScheduleService.getScheduleById(testScheduleId);
        assertThat(returnedScheduleDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        Schedule returnedSchedule = testScheduleRepository.save(testSchedule);
        ScheduleDTO returnedScheduleDTO = testScheduleService.getScheduleById(returnedSchedule.getScheduleID());

        assertThat(returnedScheduleDTO).isNotNull();
        assertThat(returnedScheduleDTO.getScheduleID()).isEqualTo(returnedSchedule.getScheduleID());
        assertThat(returnedScheduleDTO.getBeginningDate()).isEqualTo(returnedSchedule.getBeginningDate());
        assertThat(returnedScheduleDTO.getDoseCount()).isEqualTo(returnedSchedule.getDoseCount());

        testScheduleRepository.deleteById(returnedSchedule.getScheduleID());
    }

    @Test
    @Transactional
    public void testServiceDeleteSchedule() {
        // firstly save new Pet
        Schedule returnedSchedule = testScheduleRepository.save(testSchedule);
        Optional<Schedule> searchedSchedule = testScheduleRepository.findById(returnedSchedule.getScheduleID());
        assertThat(searchedSchedule.isPresent()).isEqualTo(true);

        ScheduleDTO deletedSchedule = testScheduleService.deleteSchedule(returnedSchedule.getScheduleID());

        assertThat(deletedSchedule.getScheduleID()).isEqualTo(returnedSchedule.getScheduleID());

        searchedSchedule = testScheduleRepository.findById(returnedSchedule.getScheduleID());
        assertThat(searchedSchedule.isPresent()).isEqualTo(false);
    }

    @Test
    @Transactional
    public void testServiceGetAllSchedule() {
        List<Schedule> listOfAllAddedSchedules = new ArrayList<>();

        testSchedule.setScheduleID(0);
        listOfAllAddedSchedules.add(testScheduleRepository.save(testSchedule));

        testSchedule.setScheduleID(0);
        listOfAllAddedSchedules.add(testScheduleRepository.save(testSchedule));

        testSchedule.setScheduleID(0);
        listOfAllAddedSchedules.add(testScheduleRepository.save(testSchedule));

        List<ScheduleDTO> scheduleList = testScheduleService.getAllSchedules();

        // we include seeded data before tests start. So we expect that size of returned list
        // is added_items_size + 1.
        assertThat(scheduleList.size() - 1).isEqualTo(listOfAllAddedSchedules.size());

        // @TODO CihatAltiparmak : Find more chic solution, it's hard coded.
        // check if there is seeded data's user id
        assertThat(scheduleList.stream().map(
                ScheduleDTO::getScheduleID).toList().contains(1))
                .isEqualTo(true);

        for (Schedule addedSchedule : listOfAllAddedSchedules) {
            assertThat(scheduleList.stream().map(
                    ScheduleDTO::getScheduleID).toList().contains(addedSchedule.getScheduleID()))
                    .isEqualTo(true);
        }

        for (Schedule addedSchedule : listOfAllAddedSchedules) {
            testScheduleRepository.deleteById(addedSchedule.getScheduleID());
        }
    }
}
