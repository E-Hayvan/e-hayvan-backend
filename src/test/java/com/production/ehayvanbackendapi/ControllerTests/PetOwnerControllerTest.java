package com.production.ehayvanbackendapi.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.production.ehayvanbackendapi.TestUtils.DataSeed;

import com.production.ehayvanbackendapi.DTO.PetOwnerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdateCustomerDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetOwnerDTO;
import com.production.ehayvanbackendapi.Services.PetOwnerService;

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
public class PetOwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    @Autowired
    private PetOwnerService testPetOwnerService;

    @Autowired
    private ObjectMapper objectMapper;

    private PetOwnerDTO testPetOwnerDTO;

    private CreateOrUpdatePetOwnerDTO testCreateOrUpdatePetOwnerDTO;
    private CreateOrUpdateCustomerDTO testCreateOrUpdateCustomerDTO;

    @Autowired
    DataSeed dataSeed;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    public void onEachTestStart() {
        testCreateOrUpdateCustomerDTO = new CreateOrUpdateCustomerDTO("Portakal", "Caykowsky", "cay@gmail.com", "abrakadabra");
        testCreateOrUpdatePetOwnerDTO = new CreateOrUpdatePetOwnerDTO(testCreateOrUpdateCustomerDTO);
    }

    @AfterEach
    public void tearDown() {
        testCreateOrUpdateCustomerDTO = null;
        testCreateOrUpdatePetOwnerDTO = null;
        testPetOwnerDTO = null;
    }

    @Test
    public void testGettingByIDWhichNoExists() throws Exception{
        int testPetOwnerId = 0;
        this.mockMvc.perform(get("/api/petowners/" + testPetOwnerId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGettingByIDWhichExistsInDatabase() throws Exception {
        Integer testPetOwnerId = 1;
        testPetOwnerDTO = testPetOwnerService.getPetOwnerById(testPetOwnerId);
        this.mockMvc.perform(get("/api/petowners/" + testPetOwnerId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.petOwnerID").value(testPetOwnerDTO.getPetOwnerID()));

        // assert that call it in PetOwnerControllerTest.testGettingIDWhichExistsInDatabase
        // and in PetOwnerController.Controller class by post-requesting
        verify(testPetOwnerService, times(2)).getPetOwnerById(testPetOwnerId);
    }

    @Test
    @Transactional
    public void testPostingByID() throws Exception{
        when(testPetOwnerService.postPetOwner(testCreateOrUpdatePetOwnerDTO)).thenReturn(new PetOwnerDTO());
        this.mockMvc.perform(post("/api/petowners/newowner").with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdatePetOwnerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());

        // assert that just called in PetOwnerControllerTest.testPostingID
        verify(testPetOwnerService, times(1)).postPetOwner(any());
    }

    @Test
    @Transactional
    public void testDeletingByID() throws Exception {
        // duck mockito
        // when(testPetOwnerService.deletePetOwner(testPetOwnerId)).thenReturn(new PetOwnerDTO());

        // firstly post data which is gotta delete later
        testPetOwnerDTO = testPetOwnerService.postPetOwner(testCreateOrUpdatePetOwnerDTO);
        testCreateOrUpdateCustomerDTO = new CreateOrUpdateCustomerDTO("Incir", "Caykowsky", "cay@gmail.com", "abrakadabra");
        testPetOwnerDTO = testPetOwnerService.postPetOwner(testCreateOrUpdatePetOwnerDTO);

        this.mockMvc.perform(delete("/api/petowners/" + testPetOwnerDTO.getPetOwnerID()).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.petOwnerID").value(testPetOwnerDTO.getPetOwnerID()));

        // assert that called in PetOwnerController.Controller class by delete-requesting
        verify(testPetOwnerService, times(1)).deletePetOwner(testPetOwnerDTO.getPetOwnerID());
    }
}
