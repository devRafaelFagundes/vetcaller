package com.vetcaller.controller.user.dto.response;

import com.vetcaller.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private UserType userType;
    private List<String> permissions;
}
