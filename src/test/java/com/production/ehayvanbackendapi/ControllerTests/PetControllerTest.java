package com.production.ehayvanbackendapi.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Services.PetService;
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
public class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    @Autowired
    private PetService testPetService;

    @Autowired
    private ObjectMapper objectMapper;

    private PetDTO testPetDTO;
    private CreateOrUpdatePetDTO testCreateOrUpdatePetDTO;

    @Autowired
    DataSeed dataSeed;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    public void onEachTestStart() {
        testCreateOrUpdatePetDTO = new CreateOrUpdatePetDTO("tsubasa", 17, 1, "rovesata movasata", 1);
    }

    @AfterEach
    public void tearDown() {
        testCreateOrUpdatePetDTO = null;
        testPetDTO = null;
    }

    @Test
    public void testGettingByIDWhichNoExists() throws Exception{
        int testPetId = 0;
        this.mockMvc.perform(get("/api/pets/" + testPetId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGettingByIDWhichExistsInDatabase() throws Exception {
        Integer testPetId = 1;
        testPetDTO = testPetService.getPetById(testPetId);

        this.mockMvc.perform(get("/api/pets/" + testPetId).with(httpBasic("test", "password")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.petOwnerID").value(testPetDTO.getPetID()));

        // assert that call it in PetControllerTest.testGettingIDWhichExistsInDatabase
        // and in PetController.Controller class by post-requesting
        verify(testPetService, times(2)).getPetById(testPetId);
    }

    @Test
    @Transactional
    public void testPostingByID() throws Exception{
        when(testPetService.postPet(testCreateOrUpdatePetDTO)).thenReturn(new PetDTO());
        this.mockMvc.perform(post("/api/pets").with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdatePetDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());

        // assert that just called in PetControllerTest.testPostingID
        verify(testPetService, times(1)).postPet(any());
    }

    @Test
    @Transactional
    public void testUpdatingById() throws Exception {
        Integer testPetId = 1;
        when(testPetService.updatePet(testPetId, testCreateOrUpdatePetDTO)).thenReturn(new PetDTO());

        testCreateOrUpdatePetDTO.setDescription("Lorna Shore - Death Portrait");
        this.mockMvc.perform(put("/api/pets/update/" + testPetId).with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdatePetDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.description").value(testCreateOrUpdatePetDTO.getDescription()));

        // assert that the method updatePet of testPetService is executed once
        verify(testPetService, times(1)).updatePet(anyInt(), any());
    }

    @Test
    @Transactional
    public void testDeletingById() throws Exception {
        // magical depression
        // if we uncomment this line,
        // result of testPetService.postPet(testCreateOrUpdatePetDTO) is always
        // new PetDTO(12, "ankara", 7, 8, "buz", 34, null, null)
        // when(testPetService.postPet(testCreateOrUpdatePetDTO)).thenReturn(new PetDTO(12, "ankara", 7, 8, "buz", 34, null, null));
        testCreateOrUpdatePetDTO.setPetName("Icardi");
        testPetDTO = testPetService.postPet(testCreateOrUpdatePetDTO);
        System.out.println(testPetDTO.getPetID());
        this.mockMvc.perform(delete("/api/pets/" + testPetDTO.getPetID()).with(httpBasic("test", "password"))
                        .content(objectMapper.writeValueAsString(testCreateOrUpdatePetDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.description").value(testCreateOrUpdatePetDTO.getDescription()));

        // assert that the method deletePet of testPetService is executed once.
        verify(testPetService, times(1)).deletePet(anyInt());

    }
}