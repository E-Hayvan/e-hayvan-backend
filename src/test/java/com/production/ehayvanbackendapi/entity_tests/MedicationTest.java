package com.production.ehayvanbackendapi.entity_tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

@SpringBootTest
class MedicationTest {

    @Test
    void createEntity() {
        Pet test_pet = new Pet();
        test_pet.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment(), new Appointment()));

        MedType test_med_type = new MedType();
        test_med_type.setMedType("ITUferin");

        Schedule test_schedule = new Schedule();
        test_schedule.setDoseCount(1773);

        Medication test_medication = new Medication();
        test_medication.setMedicationID(-1773);
        test_medication.setMedicationName("kafanoktaitunoktaedunoktateeree");
        test_medication.setMedTypeID(test_med_type);
        test_medication.setPetID(test_pet);
        test_medication.setScheduleID(test_schedule);

        assertThat(test_medication.getMedicationID()).isEqualTo(-1773);
        assertThat(test_medication.getMedicationName()).isEqualTo("kafanoktaitunoktaedunoktateeree");
        assertThat(test_medication.getMedTypeID().getMedType()).isEqualTo("ITUferin");
        assertThat(test_medication.getPetID().getAppointments().size()).isEqualTo(4);
        assertThat(test_medication.getScheduleID().getDoseCount()).isEqualTo(1773);
    }

}