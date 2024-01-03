package com.production.ehayvanbackendapi.EntityTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@SpringBootTest
class AppointmentTest {

    @Test
    void createEntity() {
        Pet test_pet = new Pet();
        test_pet.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment(), new Appointment()));

        PetOwner test_pet_owner = new PetOwner();
        test_pet_owner.setAppointments(List.of(new Appointment(), new Appointment(), new Appointment()));

        Veterinarian test_veterinarian = new Veterinarian();
        test_veterinarian.setClinic("apple clinic");
        test_veterinarian.setVetID(13);
        test_veterinarian.setAppointments(List.of(new Appointment(), new Appointment()));
        test_veterinarian.setUser(new Customer());

        Appointment test_appointment = new Appointment();
        test_appointment.setAppointmentID(1773);
        test_appointment.setAppointmentDate(LocalDateTime.of(2034, Month.JANUARY, 4, 9, 5));
        test_appointment.setPetID(test_pet);
        test_appointment.setVetID(test_veterinarian);
        test_appointment.setPetOwnerID(test_pet_owner);

        assertThat(test_appointment.getAppointmentID()).isEqualTo(1773);
        assertThat(test_appointment.getAppointmentDate()).isEqualTo(LocalDateTime.of(2034, Month.JANUARY, 4, 9, 5));
        assertThat(test_appointment.getPetID().getAppointments().size()).isEqualTo(4);
        assertThat(test_appointment.getVetID().getAppointments().size()).isEqualTo(2);
        assertThat(test_appointment.getPetOwnerID().getAppointments().size()).isEqualTo(3);
    }

}