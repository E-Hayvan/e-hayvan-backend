package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.Appointment;
import com.production.ehayvanbackendapi.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    @Query(value="SELECT *FROM Pet WHERE Pet.pet_ownerid = :pet_ownerid",nativeQuery = true)
    public Optional<List<Pet>> getAllPetsForPetOwner(@Param("pet_ownerid") int pet_ownerid);

    @Query(value = "SELECT COUNT(*) FROM pet", nativeQuery = true)
    public int getAllPetsCount();

}
