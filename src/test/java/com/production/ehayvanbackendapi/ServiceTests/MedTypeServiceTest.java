package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.MedTypeDTO;
import com.production.ehayvanbackendapi.DTO.MedicationDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.MedTypeMapper;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Repositories.MedTypeRepository;
import com.production.ehayvanbackendapi.Repositories.PetRepository;
import com.production.ehayvanbackendapi.Services.MedTypeService;
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
public class MedTypeServiceTest {
    @SpyBean
    @Autowired
    private MedTypeService testMedTypeService;

    @SpyBean
    @Autowired
    private MedTypeRepository testMedTypeRepository;

    @Autowired
    DataSeed dataSeed;

    private MedType testMedType;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testMedType = new MedType();
        testMedType.setMedTypeID(0);
        testMedType.setMedications(List.of());
        testMedType.setMedType("Nigde Buyuksehir Belediye Spor");
    }

    @AfterEach
    public void onEachTestEnd() {
        testMedType = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testMedTypeId = 0;
        MedTypeDTO returnedMedTypeDTO = testMedTypeService.getMedTypeById(testMedTypeId);
        assertThat(returnedMedTypeDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        MedType returnedMedType = testMedTypeRepository.save(testMedType);
        MedTypeDTO returnedMedTypeDTO = testMedTypeService.getMedTypeById(returnedMedType.getMedTypeID());

        assertThat(returnedMedTypeDTO).isNotNull();
        assertThat(returnedMedTypeDTO.getMedTypeID()).isEqualTo(returnedMedType.getMedTypeID());
        assertThat(returnedMedTypeDTO.getMedTypeName()).isEqualTo(returnedMedType.getMedType());

        testMedTypeRepository.deleteById(returnedMedType.getMedTypeID());
    }

    @Test
    @Transactional
    public void testServiceDeleteMedType() {
        // firstly save new MedType
        MedType returnedMedType = testMedTypeRepository.save(testMedType);
        Optional<MedType> searchedMedType = testMedTypeRepository.findById(returnedMedType.getMedTypeID());
        assertThat(searchedMedType.isPresent()).isEqualTo(true);

        MedTypeDTO deletedMedType = testMedTypeService.deleteMedType(returnedMedType.getMedTypeID());

        assertThat(deletedMedType.getMedTypeID()).isEqualTo(returnedMedType.getMedTypeID());

        searchedMedType = testMedTypeRepository.findById(returnedMedType.getMedTypeID());
        assertThat(searchedMedType.isPresent()).isEqualTo(false);
    }

    @Test
    @Transactional
    public void testServiceGetAllMedType() {
        List<MedType> listOfAllAddedMedTypes = new ArrayList<>();

        testMedType.setMedTypeID(0);
        listOfAllAddedMedTypes.add(testMedTypeRepository.save(testMedType));

        testMedType.setMedTypeID(0);
        listOfAllAddedMedTypes.add(testMedTypeRepository.save(testMedType));

        testMedType.setMedTypeID(0);
        listOfAllAddedMedTypes.add(testMedTypeRepository.save(testMedType));

        List<MedTypeDTO> medTypeList = testMedTypeService.getAllMedTypes();

        // we include seeded data before tests start. So we expect that size of returned list
        // is added_items_size + 3.
        assertThat(medTypeList.size() - 3).isEqualTo(listOfAllAddedMedTypes.size());

        // @TODO CihatAltiparmak : Find more chic solution, it's hard coded.
        // check if there is seeded data's user id
        assertThat(medTypeList.stream().map(
                MedTypeDTO::getMedTypeID).toList().contains(1))
                .isEqualTo(true);

        assertThat(medTypeList.stream().map(
                MedTypeDTO::getMedTypeID).toList().contains(2))
                .isEqualTo(true);

        for (MedType addedMedType : listOfAllAddedMedTypes) {
            assertThat(medTypeList.stream().map(
                    MedTypeDTO::getMedTypeID).toList().contains(addedMedType.getMedTypeID()))
                    .isEqualTo(true);
        }

        for (MedType addedMedType : listOfAllAddedMedTypes) {
            testMedTypeRepository.deleteById(addedMedType.getMedTypeID());
        }
    }
}
