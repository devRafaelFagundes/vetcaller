package com.vetcaller.controller.pet.dto.response;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CreatePetResponse {
    private Long id;
    private String name;
    private Long ownerId;
}
