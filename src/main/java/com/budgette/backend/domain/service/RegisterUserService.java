package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.User;
import com.budgette.backend.domain.port.in.RegisterUserUseCase;
import com.budgette.backend.domain.port.out.UserRepositoryPort;

import java.util.UUID;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterUserCommand command) {
        if (userRepositoryPort.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Un compte existe déjà avec cet email : " + command.email());
        }

        User user = new User(
                UUID.randomUUID().toString(),
                command.email(),
                passwordEncoder.encode(command.password()),
                command.firstName(),
                command.lastName()
        );

        return userRepositoryPort.save(user);
    }

    public interface PasswordEncoder {
        String encode(String rawPassword);
    }
}
