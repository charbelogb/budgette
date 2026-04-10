package com.budgette.simulator.controller;

import com.budgette.simulator.dto.AccountInfoDTO;
import com.budgette.simulator.dto.BalanceDTO;
import com.budgette.simulator.dto.TransactionDTO;
import com.budgette.simulator.service.MoovSimulatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/moov/accounts")
@Tag(name = "Moov Money", description = "Simulated Moov Money API endpoints")
public class MoovController {

    private static final Random RANDOM = new Random();
    private final MoovSimulatorService moovService;

    public MoovController(MoovSimulatorService moovService) {
        this.moovService = moovService;
    }

    @GetMapping("/{accountId}/balance")
    @Operation(summary = "Get Moov Money account balance")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable String accountId) {
        if (shouldSimulateError()) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok(moovService.getBalance(accountId));
    }

    @GetMapping("/{accountId}/transactions")
    @Operation(summary = "Get Moov Money account transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable String accountId) {
        if (shouldSimulateError()) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok(moovService.getTransactions(accountId));
    }

    @GetMapping("/{accountId}/info")
    @Operation(summary = "Get Moov Money account info")
    public ResponseEntity<AccountInfoDTO> getAccountInfo(@PathVariable String accountId) {
        if (shouldSimulateError()) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok(moovService.getAccountInfo(accountId));
    }

    private boolean shouldSimulateError() {
        return RANDOM.nextInt(100) < 5;
    }
}
