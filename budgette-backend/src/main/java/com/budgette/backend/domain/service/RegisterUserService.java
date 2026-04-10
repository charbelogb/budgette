package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.User;
import com.budgette.backend.domain.port.in.RegisterUserUseCase;
import com.budgette.backend.domain.port.out.UserRepositoryPort;

import java.util.UUID;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Un compte avec cet email existe déjà.");
        }

        User user = new User(
                UUID.randomUUID().toString(),
                command.email(),
                passwordEncoder.encode(command.password()),
                command.firstName(),
                command.lastName()
        );

        return userRepository.save(user);
    }

    public interface PasswordEncoder {
        String encode(String rawPassword);
    }
}
