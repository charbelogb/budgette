package com.budgette.backend.domain.port.in;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface GetDashboardUseCase {

    DashboardResult getDashboard(String userId);

    record DashboardResult(
            String userId,
            String fullName,
            BigDecimal totalBalance,
            List<Account> accounts,
            List<Transaction> recentTransactions
    ) {}
}
