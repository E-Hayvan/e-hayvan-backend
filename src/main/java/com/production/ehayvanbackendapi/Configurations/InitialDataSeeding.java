package com.production.ehayvanbackendapi.Configurations;

import com.production.ehayvanbackendapi.Entities.*;
import com.production.ehayvanbackendapi.Repositories.*;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class InitialDataSeeding implements ApplicationListener<ContextRefreshedEvent> {
    private final PetOwnerRepository petOwnerRepository;
    private final MedTypeRepository medTypeRepository;
    private final PetTypeRepository petTypeRepository;
    private final UserTypeRepository userTypeRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicationRepository medicationRepository;
    private final PetRepository petRepository;
    private final ScheduleRepository scheduleRepository;
    private final VeterinarianRepository veterinarianRepository;
    public InitialDataSeeding(PetOwnerRepository petOwnerRepo,
                              MedTypeRepository medTypeRepo,
                              PetTypeRepository petTypeRepo,
                              UserTypeRepository userTypeRepo,
                              CustomerRepository customerRepo,
                              AppointmentRepository appointmentRepo,
                              MedicationRepository medicationRepo,
                              PetRepository petRepo,
                              ScheduleRepository scheduleRepo,
                              VeterinarianRepository veterinarianRepo){
        petOwnerRepository = petOwnerRepo;
        medTypeRepository = medTypeRepo;
        petTypeRepository = petTypeRepo;
        userTypeRepository = userTypeRepo;
        appointmentRepository = appointmentRepo;
        medicationRepository = medicationRepo;
        petRepository = petRepo;
        scheduleRepository = scheduleRepo;
        veterinarianRepository = veterinarianRepo;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        List<UserType> userTypes = userTypeRepository.findAll();
//        List<PetOwner> petOwners = petOwnerRepository.findAll();
//        if(!userTypes.isEmpty()){
//            if(petOwners.isEmpty()){
//                //Create Customer for PetOwner
//                Customer exampleCustomer = new Customer();
//                exampleCustomer.setName("exampleName");
//                exampleCustomer.setSurname("exampleSurname");
//                exampleCustomer.setEmail("example@mail.com");
//                exampleCustomer.setPassword("example");
//                exampleCustomer.setUserTypeID(userTypeRepository.findById(1).get());
//
//                //Create Customer for Veterinarian
//                Customer exampleCustomerVet = new Customer();
//                exampleCustomerVet.setName("exampleVetName");
//                exampleCustomerVet.setSurname("exampleVetSurname");
//                exampleCustomerVet.setEmail("example@mailVet.com");
//                exampleCustomerVet.setPassword("example");
//                exampleCustomerVet.setUserTypeID(userTypeRepository.findById(2).get());
//
//                //Create PetOwner
//                PetOwner exampleOwner = new PetOwner();
//
//                //Connect Customer Profile of PetOwner
//                exampleOwner.setUser(exampleCustomer);
//
//                //Create Veterinarian
//                Veterinarian exampleVeterinarian = new Veterinarian();
//                exampleVeterinarian.setClinic("Example Clinic");
//
//                //Connect Veterinarian and PetOwner
//                List<PetOwner> exampleOwners = new ArrayList<>();
//                exampleOwners.add(exampleOwner);
//                exampleVeterinarian.setPetOwners(exampleOwners);
//
//                //Connect Customer Profile of Veterinarian
//                exampleVeterinarian.setUser(exampleCustomerVet);
//                exampleCustomerVet.setVet(exampleVeterinarian);
//
//                //Connect Veterinarian and PetOwner
//                exampleOwner.setVet(exampleVeterinarian);
//
//                //Create an example Pet
//                Pet examplePet = new Pet();
//                examplePet.setPetName("examplePet");
//                examplePet.setAge(2);
//                examplePet.setDescription("example description");
//                examplePet.setPetTypeID(petTypeRepository.findById(2).get());
//
//                //Connect Pet with PetOwner
//                examplePet.setPetOwnerID(exampleOwner);
//
//                //Connect PetOwner with Pet
//                List<Pet> examplePets = new ArrayList<>();
//                examplePets.add(examplePet);
//                exampleOwner.setPets(examplePets);
//
//                //Create an Appointment
//                Appointment exampleAppointment = new Appointment();
//                Date date = new Date();
//                exampleAppointment.setAppointmentDate(date);
//                exampleAppointment.setVetID(exampleVeterinarian);
//                exampleAppointment.setPetID(examplePet);
//                exampleAppointment.setPetOwnerID(exampleOwner);
//
//                //Connect Veterinarian, Owner and Pet with Appointment
//                List<Appointment> appointmentsVeterinarian = new ArrayList<>();
//                appointmentsVeterinarian.add(exampleAppointment);
//                exampleVeterinarian.setAppointments(appointmentsVeterinarian);
//
//                List<Appointment> appointmentsOwner = new ArrayList<>();
//                appointmentsOwner.add(exampleAppointment);
//                exampleOwner.setAppointments(appointmentsOwner);
//
//                List<Appointment> appointmentsPet = new ArrayList<>();
//                appointmentsPet.add(exampleAppointment);
//                examplePet.setAppointments(appointmentsPet);
//
//                //Create Schedule
//                Schedule exampleSchedule = new Schedule();
//                exampleSchedule.setBeginningDate(new Date());
//                exampleSchedule.setDoseCount(1);
//                exampleSchedule.setDoseFrequency(1);
//
//                //Create Medication
//                Medication exampleMedication = new Medication();
//                exampleMedication.setMedicationName("exampleMedication");
//                exampleMedication.setMedTypeID(medTypeRepository.findById(1).get());
//
//                //Connect Medication with Schedule
//                exampleMedication.setScheduleID(exampleSchedule);
//                List<Medication> medicationsSchedule = new ArrayList<>();
//                medicationsSchedule.add(exampleMedication);
//                exampleSchedule.setMedications(medicationsSchedule);
//
//                //Connect Pet with Medication
//                List<Medication> medicationsPet = new ArrayList<>();
//                medicationsPet.add(exampleMedication);
//                examplePet.setMedications(medicationsPet);
//
//                //Connect Medication with Pet
//                exampleMedication.setPetID(examplePet);
//
//                veterinarianRepository.save(exampleVeterinarian);
//                petOwnerRepository.save(exampleOwner);
//                petRepository.save(examplePet);
//                appointmentRepository.save(exampleAppointment);
//                scheduleRepository.save(exampleSchedule);
//                medicationRepository.save(exampleMedication);
//            }
//        }
    }
}
