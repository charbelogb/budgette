package com.budgette.simulator.controller;

import com.budgette.simulator.dto.AccountInfoDTO;
import com.budgette.simulator.dto.BalanceDTO;
import com.budgette.simulator.dto.TransactionDTO;
import com.budgette.simulator.service.MTNSimulatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/mtn/accounts")
@Tag(name = "MTN Mobile Money", description = "Simulated MTN Mobile Money API endpoints")
public class MTNController {

    private static final Random RANDOM = new Random();
    private final MTNSimulatorService mtnService;

    public MTNController(MTNSimulatorService mtnService) {
        this.mtnService = mtnService;
    }

    @GetMapping("/{accountId}/balance")
    @Operation(summary = "Get MTN account balance")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable String accountId) {
        if (shouldSimulateError()) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok(mtnService.getBalance(accountId));
    }

    @GetMapping("/{accountId}/transactions")
    @Operation(summary = "Get MTN account transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable String accountId) {
        if (shouldSimulateError()) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok(mtnService.getTransactions(accountId));
    }

    @GetMapping("/{accountId}/info")
    @Operation(summary = "Get MTN account info")
    public ResponseEntity<AccountInfoDTO> getAccountInfo(@PathVariable String accountId) {
        if (shouldSimulateError()) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok(mtnService.getAccountInfo(accountId));
    }

    private boolean shouldSimulateError() {
        return RANDOM.nextInt(100) < 5;
    }
}
