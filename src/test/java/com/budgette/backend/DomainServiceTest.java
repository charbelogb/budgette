package com.budgette.backend;

import com.budgette.backend.domain.model.*;
import com.budgette.backend.domain.service.*;
import com.budgette.backend.domain.port.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.budgette.backend.domain.port.in.RegisterUserUseCase;
import com.budgette.backend.domain.port.in.LoginUseCase;

class DomainServiceTest {

    private UserRepositoryPort userRepositoryPort;
    private RegisterUserService registerUserService;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        userRepositoryPort = mock(UserRepositoryPort.class);
        RegisterUserService.PasswordEncoder passwordEncoder = raw -> "encoded_" + raw;
        registerUserService = new RegisterUserService(userRepositoryPort, passwordEncoder);

        LoginService.PasswordVerifier passwordVerifier = (raw, encoded) -> encoded.equals("encoded_" + raw);
        LoginService.TokenGenerator tokenGenerator = (userId, email) -> "token_" + userId;
        loginService = new LoginService(userRepositoryPort, passwordVerifier, tokenGenerator);
    }

    @Test
    void shouldRegisterUser() {
        when(userRepositoryPort.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepositoryPort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        RegisterUserUseCase.RegisterUserCommand command = new RegisterUserUseCase.RegisterUserCommand(
                "test@example.com", "password123", "Jean", "Dupont"
        );

        User user = registerUserService.register(command);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Jean", user.getFirstName());
        assertEquals("Dupont", user.getLastName());
        assertEquals("encoded_password123", user.getPasswordHash());
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        when(userRepositoryPort.existsByEmail("existing@example.com")).thenReturn(true);

        RegisterUserUseCase.RegisterUserCommand command = new RegisterUserUseCase.RegisterUserCommand(
                "existing@example.com", "password123", "Jean", "Dupont"
        );

        assertThrows(IllegalArgumentException.class, () -> registerUserService.register(command));
    }

    @Test
    void shouldLoginUser() {
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, "test@example.com", "encoded_password123", "Jean", "Dupont");
        when(userRepositoryPort.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        LoginUseCase.LoginCommand command = new LoginUseCase.LoginCommand("test@example.com", "password123");
        LoginUseCase.LoginResult result = loginService.login(command);

        assertNotNull(result);
        assertEquals("token_" + userId, result.token());
        assertEquals("test@example.com", result.email());
    }

    @Test
    void shouldThrowWhenLoginWithWrongPassword() {
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, "test@example.com", "encoded_correctPassword", "Jean", "Dupont");
        when(userRepositoryPort.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        LoginUseCase.LoginCommand command = new LoginUseCase.LoginCommand("test@example.com", "wrongPassword");

        assertThrows(IllegalArgumentException.class, () -> loginService.login(command));
    }

    @Test
    void userFullNameShouldCombineFirstAndLastName() {
        User user = new User("1", "email@test.com", "hash", "Jean", "Dupont");
        assertEquals("Jean Dupont", user.getFullName());
    }
}
