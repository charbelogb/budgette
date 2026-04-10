package com.budgette.backend.domain.port.in;

import com.budgette.backend.domain.model.User;

public interface RegisterUserUseCase {

    record RegisterCommand(String email, String password, String firstName, String lastName) {}

    User register(RegisterCommand command);
}
