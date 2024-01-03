package com.production.ehayvanbackendapi.Mappers;
import com.production.ehayvanbackendapi.DTO.PetDTO;
import com.production.ehayvanbackendapi.DTO.request.CreateOrUpdatePetDTO;
import com.production.ehayvanbackendapi.Entities.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private final ModelMapper modelMapper;

    public PetMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Pet.class, PetDTO.class).addMappings(
                mapper -> {
                    mapper.map(src -> src.getPetOwnerID().getPetOwnerID(), PetDTO::setPetOwnerID);
                    mapper.map(src -> src.getPetTypeID().getPetTypeID(), PetDTO::setPetTypeID);
                }
        );
        this.modelMapper.createTypeMap(CreateOrUpdatePetDTO.class, Pet.class).addMappings(
                mapper -> {
                    mapper.skip(src -> src.getPetOwnerID(),
                            (dest, val) -> dest.getPetOwnerID().setPetOwnerID((Integer) val));
                    mapper.skip(src -> src.getPetTypeID(),
                            (dest, val) -> dest.getPetTypeID().setPetTypeID((Integer) val));
                    mapper.skip(src -> src.getPetTypeID(),
                            (dest, val) -> dest.getPetTypeID().setType((String) val));
                }
        );
    }

    public PetDTO convertToDto(Pet pet) {
        return modelMapper.map(pet, PetDTO.class);
    }

    public Pet convertToEntity(CreateOrUpdatePetDTO petDTO) {
        return modelMapper.map(petDTO, Pet.class);
    }

    public Pet mapExistingEntity(CreateOrUpdatePetDTO petDTO, Pet target){
        modelMapper.map(petDTO, target);
        return target;
    }
}

