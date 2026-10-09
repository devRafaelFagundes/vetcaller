package com.vetcaller.mapper;

import com.vetcaller.controller.user.dto.request.UserRequest;
import com.vetcaller.controller.user.dto.response.UserResponse;
import com.vetcaller.domain.Permission;
import com.vetcaller.domain.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserMapper {

    public static User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUserType(request.getUserType());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .permissions(buildPermissionsResponse(user.getPermissions()))
                .userType(user.getUserType())
                .build();
    }

    private static List<String> buildPermissionsResponse(List<Permission> permissions) {
        return permissions.stream()
                .map(Permission::getName)
                .toList();
    }
}
