package com.production.ehayvanbackendapi.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateVeterinarianDTO;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VeterinarianControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    @Autowired
    private VeterinarianService testVeterinarianService;

    @Autowired
    private ObjectMapper objectMapper;

    private VeterinarianDTO testVeterinarianDTO;
    private CreateOrUpdateVeterinarianDTO testCreateOrUpdateVeterinarianDTO;
    private CreateOrUpdateCustomerDTO testCreateOrUpdateCustomerDTO;

    @Autowired
    DataSeed dataSeed;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    public void onEachTestStart() {
        testCreateOrUpdateCustomerDTO = new CreateOrUpdateCustomerDTO("Amanda", "Tsukuba", "futbolcuyum.nokta.ben@gmail.com", "siyak ordek");
        testCreateOrUpdateVeterinarianDTO = new CreateOrUpdateVeterinarianDTO(testCreateOrUpdateCustomerDTO, "yilbasi milbasi");
    }

    @AfterEach
    public void tearDown() {
        testCreateOrUpdateVeterinarianDTO = null;
        testVeterinarianDTO = null;
        testCreateOrUpdateCustomerDTO = null;
    }

    @Test
    public void testGettingByIDWhichNoExists() throws Exception{
        int testPetId = 0;
        this.mockMvc.perform(get("/api/veterinarians/" + testPetId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGettingByIDWhichExistsInDatabase() throws Exception {
        Integer testPetId = 1;
        testVeterinarianDTO = testVeterinarianService.getVeterinarianById(testPetId);

        this.mockMvc.perform(get("/api/veterinarians/" + testPetId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.vetID").value(testVeterinarianDTO.getVetID()));

        // assert that call it in PetControllerTest.testGettingIDWhichExistsInDatabase
        // and in PetController.Controller class by post-requesting
        verify(testVeterinarianService, times(2)).getVeterinarianById(testPetId);
    }

    @Test
    @Transactional
    public void testPostingByID() throws Exception{
        when(testVeterinarianService.postVeterinarian(testCreateOrUpdateVeterinarianDTO)).thenReturn(new VeterinarianDTO());
        this.mockMvc.perform(post("/api/veterinarians").with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateVeterinarianDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());

        // assert that just called in PetControllerTest.testPostingID
        verify(testVeterinarianService, times(1)).postVeterinarian(any());
    }

    @Test
    @Transactional
    public void testDeletingById() throws Exception {
        testCreateOrUpdateVeterinarianDTO.setClinic("Sabun yaparim");
        testVeterinarianDTO = testVeterinarianService.postVeterinarian(testCreateOrUpdateVeterinarianDTO);

        this.mockMvc.perform(delete("/api/veterinarians/" + testVeterinarianDTO.getVetID()).with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdateVeterinarianDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.clinic").value(testCreateOrUpdateVeterinarianDTO.getClinic()));

        // assert that the method deletePet of testPetService is executed once.
        verify(testVeterinarianService, times(1)).deleteVeterinarian(anyInt());

    }
}
