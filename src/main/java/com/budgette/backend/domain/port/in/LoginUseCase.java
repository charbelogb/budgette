package com.budgette.backend.domain.port.in;

public interface LoginUseCase {

    LoginResult login(LoginCommand command);

    record LoginCommand(
            String email,
            String password
    ) {}

    record LoginResult(
            String token,
            String userId,
            String email,
            String firstName,
            String lastName
    ) {}
}
