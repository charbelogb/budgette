package com.budgette.backend.infrastructure.web.controller;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Country;
import com.budgette.backend.domain.model.Operator;
import com.budgette.backend.domain.port.in.AddAccountUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.infrastructure.web.dto.request.AddAccountRequest;
import com.budgette.backend.infrastructure.web.dto.response.AccountResponse;
import com.budgette.backend.infrastructure.web.mapper.WebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Gestion des comptes Mobile Money")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AddAccountUseCase addAccountUseCase;
    private final AccountRepositoryPort accountRepository;
    private final WebMapper webMapper;

    public AccountController(AddAccountUseCase addAccountUseCase,
                             AccountRepositoryPort accountRepository,
                             WebMapper webMapper) {
        this.addAccountUseCase = addAccountUseCase;
        this.accountRepository = accountRepository;
        this.webMapper = webMapper;
    }

    @GetMapping
    @Operation(summary = "Lister les comptes de l'utilisateur connecté")
    public ResponseEntity<List<AccountResponse>> getAccounts(
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        List<AccountResponse> accounts = accountRepository.findByUserId(userId).stream()
                .map(webMapper::toAccountResponse)
                .toList();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    @Operation(summary = "Ajouter un compte Mobile Money")
    public ResponseEntity<AccountResponse> addAccount(
            @Valid @RequestBody AddAccountRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        Account account = addAccountUseCase.addAccount(
                new AddAccountUseCase.AddAccountCommand(
                        userId,
                        Operator.valueOf(request.getOperator()),
                        Country.valueOf(request.getCountry()),
                        request.getPhoneNumber()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.toAccountResponse(account));
    }
}
