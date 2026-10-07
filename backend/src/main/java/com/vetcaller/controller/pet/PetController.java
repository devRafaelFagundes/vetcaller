package com.vetcaller.controller.pet;

import com.vetcaller.controller.pet.dto.request.CreatePetRequest;
import com.vetcaller.controller.pet.dto.response.CreatePetResponse;
import com.vetcaller.controller.pet.dto.response.GetPetResponse;
import com.vetcaller.domain.Pet;
import com.vetcaller.mapper.PetMapper;
import com.vetcaller.service.pet.PetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    public Page<GetPetResponse> getPets(Pageable pageable) {
        return petService.getPets(pageable).map(pet -> PetMapper.toGetPetResponse(pet));
    }

    @PostMapping
    public CreatePetResponse createPet(@Valid @RequestBody CreatePetRequest request) {
        Pet createdPet = petService.createPet(request);
        return PetMapper.toCreatePetResponse(createdPet);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }
}
