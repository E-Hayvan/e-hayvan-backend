package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
}
