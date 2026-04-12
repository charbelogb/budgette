package com.budgette.backend.application.controller;

import com.budgette.backend.application.dto.request.LoginRequest;
import com.budgette.backend.application.dto.request.RegisterRequest;
import com.budgette.backend.application.dto.response.AuthResponse;
import com.budgette.backend.domain.model.User;
import com.budgette.backend.domain.port.in.LoginUseCase;
import com.budgette.backend.domain.port.in.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentification", description = "Inscription et connexion")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription d'un nouvel utilisateur")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = registerUserUseCase.register(new RegisterUserUseCase.RegisterUserCommand(
                request.email(),
                request.password(),
                request.firstName(),
                request.lastName()
        ));

        LoginUseCase.LoginResult loginResult = loginUseCase.login(
                new LoginUseCase.LoginCommand(request.email(), request.password())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(
                loginResult.token(),
                loginResult.userId(),
                loginResult.email(),
                loginResult.firstName(),
                loginResult.lastName()
        ));
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginUseCase.LoginResult result = loginUseCase.login(
                new LoginUseCase.LoginCommand(request.email(), request.password())
        );
        return ResponseEntity.ok(new AuthResponse(
                result.token(),
                result.userId(),
                result.email(),
                result.firstName(),
                result.lastName()
        ));
    }
}
