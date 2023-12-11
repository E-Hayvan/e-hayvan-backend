package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
}