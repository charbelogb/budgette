package com.budgette.backend.application.dto.response;

public record AuthResponse(
    String token,
    String userId,
    String email,
    String firstName,
    String lastName
) {}
