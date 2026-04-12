package com.budgette.backend.application.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
    String userId,
    String fullName,
    BigDecimal totalBalance,
    String currency,
    List<AccountResponse> accounts,
    List<TransactionResponse> recentTransactions
) {}
