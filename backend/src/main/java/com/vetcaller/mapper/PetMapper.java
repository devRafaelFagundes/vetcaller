package com.vetcaller.mapper;

import com.vetcaller.controller.pet.dto.response.CreatePetResponse;
import com.vetcaller.controller.pet.dto.response.GetPetResponse;
import com.vetcaller.domain.Pet;


public class PetMapper {
    public static CreatePetResponse toCreatePetResponse(Pet createdPet) {
        return CreatePetResponse.builder()
                .id(createdPet.getId())
                .name(createdPet.getName())
                .age(createdPet.getAge())
                .ownerId(createdPet.getOwner().getId())
                .build();
    }

    public static GetPetResponse toGetPetResponse(Pet pet) {
        return GetPetResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .ownerId(pet.getOwner().getId())
                .build();
    }
}
