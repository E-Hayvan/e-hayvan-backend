package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.DTO.VeterinarianDTO;
import com.production.ehayvanbackendapi.Entities.Customer;
import com.production.ehayvanbackendapi.Entities.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer> {
    @Query(value = "SELECT veterinarian.* FROM veterinarian INNER JOIN customer ON veterinarian.userid = customer.userid WHERE name LIKE :name%",
    nativeQuery = true)
    public List<Veterinarian> findVeterinariansByName(@Param("name") String name);

    @Query(value = "SELECT COUNT(*) FROM veterinarian", nativeQuery = true)
    public int getAllVetsCount();
}
