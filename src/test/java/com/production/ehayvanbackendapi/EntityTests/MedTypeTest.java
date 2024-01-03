package com.production.ehayvanbackendapi.EntityTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MedTypeTest {

    @Test
    void createEntity() {
        MedType test_med_type = new MedType();
        test_med_type.setMedType("Cirpilmis Zenginlik");
        test_med_type.setMedTypeID(51);
        test_med_type.setMedications(List.of(new Medication(), new Medication()));

        assertThat(test_med_type.getMedType()).isEqualTo("Cirpilmis Zenginlik");
        assertThat(test_med_type.getMedTypeID()).isEqualTo(51);
        assertThat(test_med_type.getMedications().size()).isEqualTo(2);
    }

}