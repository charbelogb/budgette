package com.budgette.backend.infrastructure.web.controller;

import com.budgette.backend.domain.port.in.SyncTransactionsUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;
import com.budgette.backend.infrastructure.web.dto.response.TransactionResponse;
import com.budgette.backend.infrastructure.web.mapper.WebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Historique et synchronisation des transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final SyncTransactionsUseCase syncTransactionsUseCase;
    private final AccountRepositoryPort accountRepository;
    private final TransactionRepositoryPort transactionRepository;
    private final WebMapper webMapper;

    public TransactionController(SyncTransactionsUseCase syncTransactionsUseCase,
                                 AccountRepositoryPort accountRepository,
                                 TransactionRepositoryPort transactionRepository,
                                 WebMapper webMapper) {
        this.syncTransactionsUseCase = syncTransactionsUseCase;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.webMapper = webMapper;
    }

    @GetMapping
    @Operation(summary = "Obtenir toutes les transactions de l'utilisateur connecté")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        List<TransactionResponse> transactions = accountRepository.findByUserId(userId).stream()
                .flatMap(a -> transactionRepository.findByAccountIdOrderByDateDesc(a.getId()).stream())
                .map(webMapper::toTransactionResponse)
                .toList();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Obtenir les transactions d'un compte spécifique")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccount(
            @PathVariable String accountId) {
        List<TransactionResponse> transactions =
                transactionRepository.findByAccountIdOrderByDateDesc(accountId).stream()
                        .map(webMapper::toTransactionResponse)
                        .toList();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/account/{accountId}/sync")
    @Operation(summary = "Synchroniser les transactions d'un compte")
    public ResponseEntity<List<TransactionResponse>> syncTransactions(@PathVariable String accountId) {
        List<TransactionResponse> transactions = syncTransactionsUseCase.syncTransactions(accountId)
                .stream()
                .map(webMapper::toTransactionResponse)
                .toList();
        return ResponseEntity.ok(transactions);
    }
}
