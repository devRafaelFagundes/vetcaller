package com.vetcaller.controller.user;

import com.vetcaller.controller.user.dto.request.UserRequest;
import com.vetcaller.controller.user.dto.response.UserResponse;
import com.vetcaller.service.user.CreateUserService;
import com.vetcaller.service.user.FindUserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserService createUserService;
    private final FindUserService findUserService;

    public UserController(CreateUserService createUserService, FindUserService findUserService) {
        this.createUserService = createUserService;
        this.findUserService = findUserService;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return createUserService.create(request);
    }

    @GetMapping("/me")
    public UserResponse findAuthenticatedUser() {
        return findUserService.findAuthenticatedUser();
    }
}
