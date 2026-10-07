package com.vetcaller.service.pet;

import com.vetcaller.controller.pet.dto.request.CreatePetRequest;
import com.vetcaller.domain.Pet;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.repository.PetRepository;
import com.vetcaller.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Page<Pet> getPets(Pageable pageable) {
        Page<Pet> pets = petRepository.findAll(pageable);
        if(pets.isEmpty()) {
            throw new ResourceNotFoundException("No pets found");
        }
        return pets;
    }

    public Pet createPet(CreatePetRequest request) {
        if(userRepository.findById(request.ownerId()).isEmpty()) {
            throw new ResourceNotFoundException("Owner not found");
        }

        Pet newPet = new Pet();
        newPet.setAge(request.age());
        newPet.setName(request.name());
        newPet.setOwner(userRepository.getReferenceById(request.ownerId()));

        return petRepository.save(newPet);
    }
}
