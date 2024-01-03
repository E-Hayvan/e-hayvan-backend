package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value="SELECT *FROM Appointment WHERE Appointment.pet_ownerid= :pet_ownerid",nativeQuery = true)
    public Optional<List<Appointment>> getAppointmentsForPetOwnerId (@Param("pet_ownerid") int pet_ownerid);
    @Query(value="SELECT *FROM Appointment WHERE Appointment.vetid= :vetid",nativeQuery = true)
    public Optional<List<Appointment>> getAppointmentsForVetID(@Param("vetid") int vetid);

}
