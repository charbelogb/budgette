package com.budgette.backend.infrastructure.web.controller;

import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.in.SyncTransactionsUseCase;
import com.budgette.backend.infrastructure.web.dto.response.TransactionResponse;
import com.budgette.backend.infrastructure.web.mapper.WebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions", description = "Gestion des transactions Mobile Money")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final SyncTransactionsUseCase syncTransactionsUseCase;
    private final WebMapper webMapper;

    public TransactionController(SyncTransactionsUseCase syncTransactionsUseCase, WebMapper webMapper) {
        this.syncTransactionsUseCase = syncTransactionsUseCase;
        this.webMapper = webMapper;
    }

    @PostMapping("/sync/{accountId}")
    @Operation(summary = "Synchroniser les transactions d'un compte")
    public ResponseEntity<List<TransactionResponse>> syncTransactions(
            @PathVariable String accountId,
            Authentication authentication) {
        String userId = (String) authentication.getPrincipal();

        List<Transaction> transactions = syncTransactionsUseCase.syncTransactions(
                new SyncTransactionsUseCase.SyncCommand(userId, accountId)
        );

        List<TransactionResponse> responses = transactions.stream()
                .map(webMapper::toTransactionResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}
