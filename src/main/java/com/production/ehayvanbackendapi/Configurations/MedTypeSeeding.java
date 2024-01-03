package com.production.ehayvanbackendapi.Configurations;

import com.production.ehayvanbackendapi.Entities.MedType;
import com.production.ehayvanbackendapi.Repositories.MedTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import java.util.List;

@Configuration
public class MedTypeSeeding implements ApplicationListener<ContextRefreshedEvent> {
    private final MedTypeRepository medTypeRepository;
    public MedTypeSeeding(MedTypeRepository repository){
        medTypeRepository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<MedType> medTypes = medTypeRepository.findAll();
        if(medTypes.isEmpty()){
            MedType vaccine = new MedType();
            MedType drop = new MedType();
            MedType medication = new MedType();

            vaccine.setMedType("Vaccine");
            drop.setMedType("Drop");
            medication.setMedType("Medication");

            medTypeRepository.save(vaccine);
            medTypeRepository.save(drop);
            medTypeRepository.save(medication);
        }
    }
}
