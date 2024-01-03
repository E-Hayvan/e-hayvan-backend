package com.production.ehayvanbackendapi.EntityTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CustomerTest {

    @Test
    void createEntity() {
        PetOwner test_pet_owner = new PetOwner();
        test_pet_owner.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment()));

        Veterinarian test_veterinarian = new Veterinarian();
        test_veterinarian.setClinic("apple clinic");
        test_veterinarian.setVetID(13);
        test_veterinarian.setAppointments(List.of(new Appointment(), new Appointment()));
        test_veterinarian.setUser(new Customer());

        Customer test_customer = new Customer();
        test_customer.setEmail("hoppalaaa@itu.edu.tr");
        test_customer.setName("crazy taxi");
        test_customer.setSurname("samsun galaksiy");
        test_customer.setPassword("moldavian rhapsody");
        test_customer.setUserID(5);
        test_customer.setUserTypeID(new UserType());
        test_customer.setVet(test_veterinarian);
        test_customer.setOwner(test_pet_owner);

        assertThat(test_customer.getEmail()).isEqualTo("hoppalaaa@itu.edu.tr");
        assertThat(test_customer.getName()).isEqualTo("crazy taxi");
        assertThat(test_customer.getSurname()).isEqualTo("samsun galaksiy");
        assertThat(test_customer.getPassword()).isEqualTo("moldavian rhapsody");
        assertThat(test_customer.getUserID()).isEqualTo(5);
        assertThat(test_customer.getUserTypeID()).isNotNull();
        assertThat(test_customer.getVet().getClinic()).isEqualTo("apple clinic");
        assertThat(test_customer.getOwner().getAppointments().size()).isEqualTo(3);
    }

}