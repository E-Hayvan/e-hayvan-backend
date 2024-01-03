package com.production.ehayvanbackendapi.EntityTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PetOwnerTest {

    @Test
    void createEntity() {
        Customer test_customer = new Customer();
        test_customer.setUserID(41);

        Veterinarian test_veterinarian = new Veterinarian();
        test_veterinarian.setClinic("apple clinic");
        test_veterinarian.setVetID(13);
        test_veterinarian.setAppointments(List.of(new Appointment(), new Appointment()));
        test_veterinarian.setUser(new Customer());

        PetOwner test_pet_owner = new PetOwner();
        test_pet_owner.setPetOwnerID(17);
        test_pet_owner.setPets(List.of(new Pet(), new Pet()));
        test_pet_owner.setUser(test_customer);
        test_pet_owner.setVet(test_veterinarian);
        test_pet_owner.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment()));

        assertThat(test_pet_owner.getPetOwnerID()).isEqualTo(17);
        assertThat(test_pet_owner.getPets().size()).isEqualTo(2);
        assertThat(test_pet_owner.getUser().getUserID()).isEqualTo(41);
        assertThat(test_pet_owner.getVet().getClinic()).isEqualTo("apple clinic");
        assertThat(test_pet_owner.getAppointments().size()).isEqualTo(3);
    }

}