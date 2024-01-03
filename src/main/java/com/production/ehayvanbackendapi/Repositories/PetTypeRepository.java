package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
}
