package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT * FROM customer c WHERE c.email = :email AND c.password = :password",
    nativeQuery = true)
    public Optional<Customer> findCustomerByEmailAndPassword(@Param("email") String email,
                                                             @Param("password") String password);

    @Query(value = "SELECT * FROM customer c WHERE c.email = :email",
            nativeQuery = true)
    public Optional<Customer> findByEmail(@Param("email") String email);

}
