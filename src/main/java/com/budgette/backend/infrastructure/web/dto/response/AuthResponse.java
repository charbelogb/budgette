package com.budgette.backend.infrastructure.web.dto.response;

public record AuthResponse(
    String token,
    String userId,
    String email,
    String firstName,
    String lastName
) {}
