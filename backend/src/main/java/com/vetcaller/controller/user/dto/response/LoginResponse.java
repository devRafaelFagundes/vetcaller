package com.vetcaller.controller.user.dto.response;

public record LoginResponse(String accessToken, Long expiresIn) {
}
