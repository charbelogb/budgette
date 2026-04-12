package com.budgette.backend.domain.port.in;

import com.budgette.backend.domain.model.User;

public interface RegisterUserUseCase {

    User register(RegisterUserCommand command);

    record RegisterUserCommand(
            String email,
            String password,
            String firstName,
            String lastName
    ) {}
}
