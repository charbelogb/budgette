package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.User;
import com.budgette.backend.domain.port.in.LoginUseCase;
import com.budgette.backend.domain.port.out.UserRepositoryPort;

public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordVerifier passwordVerifier;
    private final TokenGenerator tokenGenerator;

    public LoginService(UserRepositoryPort userRepository,
                        PasswordVerifier passwordVerifier,
                        TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordVerifier = passwordVerifier;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public LoginResult login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect."));

        if (!passwordVerifier.verify(command.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect.");
        }

        String token = tokenGenerator.generate(user.getId(), user.getEmail());

        return new LoginResult(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                token
        );
    }

    public interface PasswordVerifier {
        boolean verify(String rawPassword, String encodedPassword);
    }

    public interface TokenGenerator {
        String generate(String userId, String email);
    }
}
