package com.vetcaller.validation;

import com.vetcaller.domain.User;
import com.vetcaller.exceptions.ResourceNotFoundException;
import com.vetcaller.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public User validateUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No user was found;");
        }
        return user.get();
    }

    public User validateUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("No user was found;");
        }
        return user.get();
    }

    public void validateUserHasPetById(Long userId, Long petId) {
        User user = validateUserById(userId);
        if (user.getPets().stream().anyMatch(pet -> pet.getId().equals(petId))) {
            throw new ResourceNotFoundException("User with id " + userId + " does not have a pet with id " + petId);
        }
    }
}
