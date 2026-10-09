package com.vetcaller.controller.pet.dto.request;

public record CreatePetRequest(
    String name,
    String species,
    Long ownerId
) {
}
