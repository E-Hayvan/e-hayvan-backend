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
class VeterinarianTest {

    @Test
    void createEntity() {
        Customer test_customer = new Customer();
        test_customer.setEmail("hoppalaaa@itu.edu.tr");

        Veterinarian test_veterinarian = new Veterinarian();
        test_veterinarian.setClinic("apple clinic");
        test_veterinarian.setVetID(13);
        test_veterinarian.setAppointments(List.of(new Appointment(), new Appointment()));
        test_veterinarian.setUser(test_customer);

        assertThat(test_veterinarian.getClinic()).isEqualTo("apple clinic");
        assertThat(test_veterinarian.getVetID()).isEqualTo(13);
        assertThat(test_veterinarian.getAppointments().size()).isEqualTo(2);
        assertThat(test_veterinarian.getUser().getEmail()).isEqualTo("hoppalaaa@itu.edu.tr");
    }

}