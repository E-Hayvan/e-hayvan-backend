package com.production.ehayvanbackendapi.entity_tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ScheduleTest {

    @Test
    void createEntity() {
        Schedule test_schedule = new Schedule();
        test_schedule.setDoseCount(15);
        test_schedule.setScheduleID(1915);
        test_schedule.setBeginningDate(new Date(3451));
        test_schedule.setMedications(List.of(new Medication()));
        test_schedule.setDoseFrequency(20);

        assertThat(test_schedule.getDoseCount()).isEqualTo(15);
        assertThat(test_schedule.getScheduleID()).isEqualTo(1915);
        assertThat(test_schedule.getBeginningDate()).isEqualTo(new Date(3451));
        assertThat(test_schedule.getDoseFrequency()).isEqualTo(20);
    }

}