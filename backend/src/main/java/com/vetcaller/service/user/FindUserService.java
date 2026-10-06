package com.vetcaller.service.user;

import com.vetcaller.controller.user.dto.response.UserResponse;
import com.vetcaller.domain.User;
import com.vetcaller.mapper.UserMapper;
import com.vetcaller.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindUserService {

    private final AuthenticatedUserService authenticatedUserService;
    private final UserRepository userRepository;

    public FindUserService(AuthenticatedUserService authenticatedUserService, UserRepository userRepository) {
        this.authenticatedUserService = authenticatedUserService;
        this.userRepository = userRepository;
    }

    public UserResponse findAuthenticatedUser() {
        User authenticatedUser = authenticatedUserService.get();
        return UserMapper.toResponse(authenticatedUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
