package com.vetcaller.validation;

import com.vetcaller.domain.Pet;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PetValidator {

    private final PetRepository petRepository;

    public Pet searchPetId(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " does not exist."));
    }

    public boolean isPetNameUniqueForOwner(String name, Long ownerId) {
        return !petRepository.existsByNameAndOwner_Id(name, ownerId);
    }


}
