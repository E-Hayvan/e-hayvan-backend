package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.MedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedTypeRepository extends JpaRepository<MedType, Integer> {
}
