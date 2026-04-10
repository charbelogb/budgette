package com.budgette.backend.domain.port.in;

public interface LoginUseCase {

    record LoginCommand(String email, String password) {}

    record LoginResult(String userId, String email, String firstName, String lastName, String token) {}

    LoginResult login(LoginCommand command);
}
