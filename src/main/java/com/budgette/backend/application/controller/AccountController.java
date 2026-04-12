package com.budgette.backend.application.controller;

import com.budgette.backend.application.dto.request.AddAccountRequest;
import com.budgette.backend.application.dto.response.AccountResponse;
import com.budgette.backend.application.mapper.WebMapper;
import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.port.in.AddAccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Comptes", description = "Gestion des comptes Mobile Money")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AddAccountUseCase addAccountUseCase;
    private final WebMapper webMapper;

    public AccountController(AddAccountUseCase addAccountUseCase, WebMapper webMapper) {
        this.addAccountUseCase = addAccountUseCase;
        this.webMapper = webMapper;
    }

    @PostMapping
    @Operation(summary = "Ajouter un compte Mobile Money")
    public ResponseEntity<AccountResponse> addAccount(
            @Valid @RequestBody AddAccountRequest request,
            Authentication authentication) {
        String userId = (String) authentication.getPrincipal();

        Account account = addAccountUseCase.addAccount(new AddAccountUseCase.AddAccountCommand(
                userId,
                request.operator(),
                request.country(),
                request.phoneNumber(),
                request.accountId()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.toAccountResponse(account));
    }
}
