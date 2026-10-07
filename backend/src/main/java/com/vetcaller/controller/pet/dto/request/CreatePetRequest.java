package com.vetcaller.controller.pet.dto.request;

public record CreatePetRequest(
    String name,
    String species,
    int age,
    Long ownerId
) {
}
