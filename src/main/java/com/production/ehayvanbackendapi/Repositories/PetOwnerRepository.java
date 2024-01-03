package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.Pet;
import com.production.ehayvanbackendapi.Entities.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {
    @Query(value="SELECT *FROM pet_owner WHERE pet_owner.vetid = :vetid",nativeQuery = true)
    public Optional<List<PetOwner>> getAllPetsForPetOwner(@Param("vetid") int vetid);

    @Query(value = "SELECT COUNT(*) FROM pet_owner", nativeQuery = true)
    public int getAllPetOwnersCount();
}

