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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public Page<GetPetResponse> getPets(Pageable pageable) {
        return petService.getPets(pageable).map(pet -> PetMapper.toGetPetResponse(pet));
    }

    @GetMapping("/me")
    public Page<GetPetResponse> getPetsForAuthenticatedUser(Pageable pageable) {
        return petService.getMyPets(pageable).map(pet -> PetMapper.toGetPetResponse(pet));
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

    @GetMapping("/user/{userId}")
    public List<GetPetResponse> getPetsByUserId(@PathVariable Long userId){
        return petService.getPetsByUserId(userId).stream().map(pet -> PetMapper.toGetPetResponse(pet)).toList();
    }
}
