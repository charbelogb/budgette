package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.in.GetDashboardUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class GetDashboardService implements GetDashboardUseCase {

    private final AccountRepositoryPort accountRepository;
    private final TransactionRepositoryPort transactionRepository;

    public GetDashboardService(AccountRepositoryPort accountRepository,
                               TransactionRepositoryPort transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public DashboardData getDashboard(String userId) {
        List<Account> accounts = accountRepository.findByUserId(userId).stream()
                .filter(Account::isActive)
                .toList();

        BigDecimal totalBalance = accounts.stream()
                .map(a -> a.getBalance() != null ? a.getBalance() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> recentTransactions = accounts.stream()
                .flatMap(a -> transactionRepository.findByAccountIdOrderByDateDesc(a.getId()).stream())
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .limit(10)
                .toList();

        return new DashboardData(totalBalance, accounts, recentTransactions);
    }
}
