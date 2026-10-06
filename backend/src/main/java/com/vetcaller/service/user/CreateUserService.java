package com.vetcaller.service.user;

import com.vetcaller.controller.user.dto.request.UserRequest;
import com.vetcaller.controller.user.dto.response.UserResponse;
import com.vetcaller.domain.Permission;
import com.vetcaller.domain.User;
import com.vetcaller.mapper.UserMapper;
import com.vetcaller.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(UserRequest request) {
        Optional<User> userOption = userRepository.findByEmail(request.getEmail());

        if (userOption.isPresent()) {
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        request.getPermissions()
                .forEach(name -> user.addPermission(Permission.builder().name(name.toString()).build()));

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }
}
