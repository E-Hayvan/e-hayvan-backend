package com.production.ehayvanbackendapi.Repositories;

import com.production.ehayvanbackendapi.Entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

}
