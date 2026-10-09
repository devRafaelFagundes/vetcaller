package com.vetcaller.controller.user.dto.request;

import com.vetcaller.domain.enums.Permissions;
import com.vetcaller.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String name;

    @NotNull
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserType userType;

    @NotNull
    @NotEmpty
    private List<Permissions> permissions;
}
