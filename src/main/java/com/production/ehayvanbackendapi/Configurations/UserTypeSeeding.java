package com.production.ehayvanbackendapi.Configurations;

import com.production.ehayvanbackendapi.Entities.UserType;
import com.production.ehayvanbackendapi.Repositories.UserTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserTypeSeeding implements ApplicationListener<ContextRefreshedEvent> {
    private final UserTypeRepository userTypeRepository;
    public UserTypeSeeding(UserTypeRepository repository){
        userTypeRepository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<UserType> userTypes = userTypeRepository.findAll();
        if(userTypes.isEmpty()){
            UserType petOwner = new UserType();
            UserType veterinarian = new UserType();
            UserType administrator = new UserType();

            petOwner.setType("Pet Owner");
            veterinarian.setType("Veterinarian");
            administrator.setType("Administrator");

            userTypeRepository.save(petOwner);
            userTypeRepository.save(veterinarian);
            userTypeRepository.save(administrator);
        }
    }
}
