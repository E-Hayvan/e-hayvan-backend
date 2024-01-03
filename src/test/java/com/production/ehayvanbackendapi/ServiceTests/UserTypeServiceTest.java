package com.production.ehayvanbackendapi.ServiceTests;

import com.production.ehayvanbackendapi.DTO.MedTypeDTO;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.PetTypeDTO;
import com.production.ehayvanbackendapi.DTO.UserTypeDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Mappers.MedTypeMapper;
import com.production.ehayvanbackendapi.Mappers.PetMapper;
import com.production.ehayvanbackendapi.Repositories.MedTypeRepository;
import com.production.ehayvanbackendapi.Repositories.PetRepository;
import com.production.ehayvanbackendapi.Repositories.PetTypeRepository;
import com.production.ehayvanbackendapi.Repositories.UserTypeRepository;
import com.production.ehayvanbackendapi.Services.MedTypeService;
import com.production.ehayvanbackendapi.Services.PetService;
import com.production.ehayvanbackendapi.Services.PetTypeService;
import com.production.ehayvanbackendapi.Services.UserTypeService;
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
public class UserTypeServiceTest {
    @SpyBean
    @Autowired
    private UserTypeService testUserTypeService;

    @SpyBean
    @Autowired
    private UserTypeRepository testUserTypeRepository;

    @Autowired
    DataSeed dataSeed;

    private UserType testUserType;

    @BeforeAll
    public void setUp() {
        dataSeed.loadSeedToDatabase();
    }

    @BeforeEach
    @Transactional
    public void onEachTestStart() {
        testUserType = new UserType();
        testUserType.setUserTypeID(0);
        testUserType.setType("Ingiliz Helvacisi");
        testUserType.setUsers(List.of());
    }

    @AfterEach
    public void onEachTestEnd() {
        testUserType = null;
    }

    @Test
    public void testServiceGetByIdWhichNoExists() {
        int testUserTypeId = 0;
        UserTypeDTO returnedUserTypeDTO = testUserTypeService.getUserTypeById(testUserTypeId);
        assertThat(returnedUserTypeDTO).isNull();
    }

    @Test
    @Transactional
    public void testServiceGetByIdWhichInDatabase() {
        UserType returnedUserType = testUserTypeRepository.save(testUserType);
        UserTypeDTO returnedUserTypeDTO = testUserTypeService.getUserTypeById(returnedUserType.getUserTypeID());

        assertThat(returnedUserTypeDTO).isNotNull();
        assertThat(returnedUserTypeDTO.getUserTypeID()).isEqualTo(returnedUserType.getUserTypeID());
        assertThat(returnedUserTypeDTO.getType()).isEqualTo(returnedUserType.getType());

        testUserTypeRepository.deleteById(returnedUserType.getUserTypeID());
    }

    @Test
    @Transactional
    public void testServiceGetAllUserType() {
        List<UserType> listOfAllAddedUserTypes = new ArrayList<>();

        testUserType.setUserTypeID(0);
        listOfAllAddedUserTypes.add(testUserTypeRepository.save(testUserType));

        testUserType.setUserTypeID(0);
        listOfAllAddedUserTypes.add(testUserTypeRepository.save(testUserType));

        testUserType.setUserTypeID(0);
        listOfAllAddedUserTypes.add(testUserTypeRepository.save(testUserType));

        List<UserTypeDTO> UserTypeList = testUserTypeService.getAllUserTypes();

        // we include seeded data before tests start. So we expect that size of returned list
        // is added_items_size + 4.
        assertThat(UserTypeList.size() - 3).isEqualTo(listOfAllAddedUserTypes.size());

        // @TODO CihatAltiparmak : Find more chic solution, it's hard coded.
        // check if there is seeded data's user id
        assertThat(UserTypeList.stream().map(
                UserTypeDTO::getUserTypeID).toList().contains(1))
                .isEqualTo(true);

        assertThat(UserTypeList.stream().map(
                UserTypeDTO::getUserTypeID).toList().contains(2))
                .isEqualTo(true);

        assertThat(UserTypeList.stream().map(
                UserTypeDTO::getUserTypeID).toList().contains(3))
                .isEqualTo(true);

        for (UserType addedUserType : listOfAllAddedUserTypes) {
            assertThat(UserTypeList.stream().map(
                    UserTypeDTO::getUserTypeID).toList().contains(addedUserType.getUserTypeID()))
                    .isEqualTo(true);
        }

        for (UserType addedUserType : listOfAllAddedUserTypes) {
            testUserTypeRepository.deleteById(addedUserType.getUserTypeID());
        }
    }
}
