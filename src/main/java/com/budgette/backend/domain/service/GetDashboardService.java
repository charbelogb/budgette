package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.model.User;
import com.budgette.backend.domain.port.in.GetDashboardUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;
import com.budgette.backend.domain.port.out.UserRepositoryPort;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetDashboardService implements GetDashboardUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public GetDashboardService(UserRepositoryPort userRepositoryPort,
                               AccountRepositoryPort accountRepositoryPort,
                               TransactionRepositoryPort transactionRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public DashboardResult getDashboard(String userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable : " + userId));

        List<Account> accounts = accountRepositoryPort.findByUserId(userId);

        BigDecimal totalBalance = accounts.stream()
                .filter(Account::isActive)
                .map(Account::getBalance)
                .filter(b -> b != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> recentTransactions = accounts.stream()
                .flatMap(account -> transactionRepositoryPort.findByAccountId(account.getId()).stream())
                .sorted(Comparator.comparing(Transaction::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(10)
                .collect(Collectors.toList());

        return new DashboardResult(userId, user.getFullName(), totalBalance, accounts, recentTransactions);
    }
}
