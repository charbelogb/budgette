package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.in.SyncTransactionsUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

public class SyncTransactionsService implements SyncTransactionsUseCase {

    private final AccountRepositoryPort accountRepository;
    private final TransactionRepositoryPort transactionRepository;
    private final MobileMoneyProviderPort mobileMoneyProvider;

    public SyncTransactionsService(AccountRepositoryPort accountRepository,
                                   TransactionRepositoryPort transactionRepository,
                                   MobileMoneyProviderPort mobileMoneyProvider) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.mobileMoneyProvider = mobileMoneyProvider;
    }

    @Override
    public List<Transaction> syncTransactions(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable : " + accountId));

        LocalDateTime from = LocalDateTime.now().minusDays(30);
        LocalDateTime to = LocalDateTime.now();

        List<Transaction> remoteTransactions =
                mobileMoneyProvider.getTransactions(account.getOperator(), account.getAccountId(), from, to);

        List<Transaction> newTransactions = remoteTransactions.stream()
                .filter(t -> !transactionRepository.existsByExternalId(t.getExternalId()))
                .toList();

        if (!newTransactions.isEmpty()) {
            transactionRepository.saveAll(newTransactions.stream()
                    .map(t -> {
                        t.setAccountId(accountId);
                        return t;
                    })
                    .toList());
        }

        MobileMoneyProviderPort.Balance balance =
                mobileMoneyProvider.getBalance(account.getOperator(), account.getAccountId());
        account.setBalance(balance.amount());
        accountRepository.save(account);

        return transactionRepository.findByAccountIdOrderByDateDesc(accountId);
    }
}
