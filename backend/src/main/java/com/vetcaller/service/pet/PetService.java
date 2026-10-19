package com.vetcaller.service.pet;

import com.vetcaller.controller.pet.dto.request.CreatePetRequest;
import com.vetcaller.domain.Pet;
import com.vetcaller.domain.User;
import com.vetcaller.exceptions.ForbiddenException;
import com.vetcaller.exceptions.ResourceAlreadyInUseException;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.repository.PetRepository;
import com.vetcaller.repository.UserRepository;
import com.vetcaller.service.user.AuthenticatedUserService;
import com.vetcaller.validation.PetValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final PetValidator petValidator;

    public Page<Pet> getPets(Pageable pageable) {
        Page<Pet> pets = petRepository.findAll(pageable);
        if(pets.isEmpty()) {
            throw new ResourceNotFoundException("No pets found");
        }
        return pets;
    }

    @Transactional
    public Pet createPet(CreatePetRequest request) {
        Optional<User> owner = userRepository.findById(request.ownerId());

        if(owner.isEmpty()) {
            throw new ResourceNotFoundException("Owner not found");
        }

        if(!petValidator.isPetNameUniqueForOwner(request.name(), request.ownerId())) {
            throw new ResourceAlreadyInUseException("A pet with the same name already exists for this owner");
        }

        Pet newPet = new Pet();
        newPet.setName(request.name());
        newPet.setOwner(userRepository.getReferenceById(request.ownerId()));

        return petRepository.save(newPet);
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        User user = authenticatedUserService.get();
        if(!pet.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not the owner of this pet");
        }

        petRepository.delete(pet);
    }

    public Page<Pet> getMyPets(Pageable pageable) {
        User user = authenticatedUserService.get();
        Page<Pet> pets = petRepository.findByOwner(user.getId(), pageable);

        if(pets.isEmpty()) {
            throw new ResourceNotFoundException("You currently have no pets, please add at least one pet to your account");
        }

        return pets;
    }

    public List<Pet> getPetsByUserId(Long userId) {
        return petRepository.findByOwner(userId);
    }
}
