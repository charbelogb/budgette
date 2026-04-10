package com.budgette.backend.infrastructure.web.controller;

import com.budgette.backend.domain.model.User;
import com.budgette.backend.domain.port.in.LoginUseCase;
import com.budgette.backend.domain.port.in.RegisterUserUseCase;
import com.budgette.backend.infrastructure.web.dto.request.LoginRequest;
import com.budgette.backend.infrastructure.web.dto.request.RegisterRequest;
import com.budgette.backend.infrastructure.web.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints d'authentification")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Créer un nouveau compte utilisateur")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = registerUserUseCase.register(
                new RegisterUserUseCase.RegisterCommand(
                        request.getEmail(),
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(user.getId(), user.getEmail(),
                        user.getFirstName(), user.getLastName(), null));
    }

    @PostMapping("/login")
    @Operation(summary = "Se connecter")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginUseCase.LoginResult result = loginUseCase.login(
                new LoginUseCase.LoginCommand(request.getEmail(), request.getPassword())
        );
        return ResponseEntity.ok(new AuthResponse(
                result.userId(), result.email(), result.firstName(), result.lastName(), result.token()
        ));
    }
}
