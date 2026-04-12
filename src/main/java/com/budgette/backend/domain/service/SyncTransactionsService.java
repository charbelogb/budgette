package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.in.SyncTransactionsUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SyncTransactionsService implements SyncTransactionsUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final MobileMoneyProviderPort mobileMoneyProviderPort;

    public SyncTransactionsService(AccountRepositoryPort accountRepositoryPort,
                                   TransactionRepositoryPort transactionRepositoryPort,
                                   MobileMoneyProviderPort mobileMoneyProviderPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.mobileMoneyProviderPort = mobileMoneyProviderPort;
    }

    @Override
    public List<Transaction> syncTransactions(SyncCommand command) {
        Account account = accountRepositoryPort.findById(command.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable : " + command.accountId()));

        if (!account.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("Ce compte ne vous appartient pas.");
        }

        LocalDateTime from = LocalDateTime.now().minusDays(30);
        LocalDateTime to = LocalDateTime.now();

        List<Transaction> remoteTransactions =
                mobileMoneyProviderPort.getTransactions(account.getOperator(), account.getAccountId(), from, to);

        List<Transaction> newTransactions = remoteTransactions.stream()
                .filter(t -> t.getExternalId() != null
                        && !transactionRepositoryPort.existsByExternalId(t.getExternalId()))
                .collect(Collectors.toList());

        if (!newTransactions.isEmpty()) {
            transactionRepositoryPort.saveAll(newTransactions);
        }

        MobileMoneyProviderPort.Balance balance =
                mobileMoneyProviderPort.getBalance(account.getOperator(), account.getAccountId());
        account.setBalance(balance.amount());
        accountRepositoryPort.save(account);

        return transactionRepositoryPort.findByAccountId(command.accountId());
    }
}
