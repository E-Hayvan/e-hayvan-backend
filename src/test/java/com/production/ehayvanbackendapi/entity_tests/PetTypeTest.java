package com.production.ehayvanbackendapi.entity_tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.sql.Date;
import java.util.List;

@SpringBootTest
class PetTypeTest {

    @Test
    void createEntity() {
        PetType test_pet_type = new PetType();
        test_pet_type.setPetTypeID(36);
        test_pet_type.setPets(List.of(new Pet(), new Pet()));
        test_pet_type.setType("micky mouse");

        assertThat(test_pet_type.getPetTypeID()).isEqualTo(36);
        assertThat(test_pet_type.getPets().size()).isEqualTo(2);
        assertThat(test_pet_type.getType()).isEqualTo("micky mouse");
    }

}