package com.production.ehayvanbackendapi.EntityTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PetTest {

    @Test
    void createEntity() {
        PetType test_pet_type = new PetType();
        test_pet_type.setType("bukale munn");
        test_pet_type.setPets(List.of(new Pet()));
        test_pet_type.setPetTypeID(12);

        PetOwner test_pet_owner = new PetOwner();
        test_pet_owner.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment()));

        Pet test_pet = new Pet();
        test_pet.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment(), new Appointment()));
        test_pet.setAge(100);
        test_pet.setPetID(34);
        test_pet.setPetName("Ketiii");
        test_pet.setDescription("Sinav oncesi uzun gecelerin vaz gecilmezi, tek basina bir mutluluk kaynagı (kediii den bahsetmiyom)");
        test_pet.setPetOwnerID(test_pet_owner);
        test_pet.setMedications(List.of(new Medication()));
        test_pet.setPetTypeID(test_pet_type);

        assertThat(test_pet.getAppointments().size()).isEqualTo(4);
        assertThat(test_pet.getAge()).isEqualTo(100);
        assertThat(test_pet.getPetID()).isEqualTo(34);
        assertThat(test_pet.getPetName()).isEqualTo("Ketiii");
        assertThat(test_pet.getDescription()).isEqualTo("Sinav oncesi uzun gecelerin vaz gecilmezi, tek basina bir mutluluk kaynagı (kediii den bahsetmiyom)");
        assertThat(test_pet.getPetOwnerID().getAppointments().size()).isEqualTo(3);
        assertThat(test_pet.getMedications().size()).isEqualTo(1);
        assertThat(test_pet.getPetTypeID().getType()).isEqualTo("bukale munn");
    }

}