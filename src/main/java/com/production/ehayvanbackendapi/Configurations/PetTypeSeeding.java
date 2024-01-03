package com.production.ehayvanbackendapi.Configurations;

import com.production.ehayvanbackendapi.Entities.Pet;
import com.production.ehayvanbackendapi.Entities.PetType;
import com.production.ehayvanbackendapi.Repositories.PetTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Configuration
public class PetTypeSeeding implements ApplicationListener<ContextRefreshedEvent> {
    private final PetTypeRepository petTypeRepository;
    public PetTypeSeeding(PetTypeRepository repository){
        petTypeRepository = repository;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<PetType> petTypes = petTypeRepository.findAll();
        if(petTypes.isEmpty()){
            PetType cat = new PetType();
            PetType dog = new PetType();
            PetType bird = new PetType();
            PetType other = new PetType();

            cat.setType("Cat");
            dog.setType("Dog");
            bird.setType("Bird");
            other.setType("Other");

            petTypeRepository.save(cat);
            petTypeRepository.save(dog);
            petTypeRepository.save(bird);
            petTypeRepository.save(other);
        }
    }
}
