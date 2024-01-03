package com.production.ehayvanbackendapi.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.ScheduleDTO;
import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateMedicationDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateScheduleDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateVeterinarianDTO;
import com.production.ehayvanbackendapi.Services.MedicationService;
import com.production.ehayvanbackendapi.Services.VeterinarianService;
import com.production.ehayvanbackendapi.TestUtils.DataSeed;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    @Autowired
    private MedicationService testMedicationService;

    @Autowired
    private ObjectMapper objectMapper;

    private MedicationDTO testMedicationDTO;
    private CreateOrUpdateMedicationDTO testCreateOrUpdateMedicationDTO;
    private CreateOrUpdateScheduleDTO testCreateOrUpdateScheduleDTO;

    @Autowired
    DataSeed dataSeed;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    public void onEachTestStart() {
        testCreateOrUpdateScheduleDTO = new CreateOrUpdateScheduleDTO();
        testCreateOrUpdateMedicationDTO = new CreateOrUpdateMedicationDTO("elma", 1, testCreateOrUpdateScheduleDTO, 1);
        testCreateOrUpdateMedicationDTO.getScheduleID().setBeginningDate(LocalDate.of(2034, Month.JANUARY, 1));
        testCreateOrUpdateMedicationDTO.getScheduleID().setDoseCount(54);
        testCreateOrUpdateMedicationDTO.getScheduleID().setDoseFrequency(100);
    }

    @AfterEach
    public void tearDown() {
        testCreateOrUpdateMedicationDTO = null;
    }

    @Test
    public void testGettingByIDWhichNoExists() throws Exception{
        int testPetId = 0;
        this.mockMvc.perform(get("/api/medications/" + testPetId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGettingByIDWhichExistsInDatabase() throws Exception {
        Integer testPetId = 1;
        testMedicationDTO = testMedicationService.getMedicationById(testPetId);

        this.mockMvc.perform(get("/api/medications/" + testPetId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.medicationID").value(testMedicationDTO.getMedicationID()))
                .andExpect(jsonPath("$.medicationName").value(testMedicationDTO.getMedicationName()));

        // assert that call it in MedicationControllerTest.testGettingIDWhichExistsInDatabase
        // and in MedicationController.Controller class by post-requesting
        verify(testMedicationService, times(2)).getMedicationById(testPetId);
    }

    @Test
    @Transactional
    public void testPostingByID() throws Exception{
        when(testMedicationService.postMedication(testCreateOrUpdateMedicationDTO)).thenReturn(new MedicationDTO());
        this.mockMvc.perform(post("/api/medications").with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateMedicationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());

        // assert that just called in MedicationControllerTest.testPostingID
        verify(testMedicationService, times(1)).postMedication(any());
    }

    @Test
    @Transactional
    public void testDeletingById() throws Exception {
        testCreateOrUpdateMedicationDTO.setMedicationName("Muzlu Oralet");
        testMedicationDTO = testMedicationService.postMedication(testCreateOrUpdateMedicationDTO);

        this.mockMvc.perform(delete("/api/medications/" + testMedicationDTO.getMedicationID()).with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateMedicationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.medicationName").value(testCreateOrUpdateMedicationDTO.getMedicationName()));

        MedicationDTO searchedMedicationDTO = testMedicationService.getMedicationById(testMedicationDTO.getMedicationID());
        assertThat(searchedMedicationDTO).isNull();

        // assert that the method deleteMedication of testMedicationService is executed once.
        verify(testMedicationService, times(1)).deleteMedication(anyInt());

    }
}
