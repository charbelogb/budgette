package com.budgette.backend.infrastructure.web.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {

    private BigDecimal totalBalance;
    private String currency;
    private List<AccountResponse> accounts;
    private List<TransactionResponse> recentTransactions;

    public DashboardResponse() {}

    public DashboardResponse(BigDecimal totalBalance, String currency,
                             List<AccountResponse> accounts,
                             List<TransactionResponse> recentTransactions) {
        this.totalBalance = totalBalance;
        this.currency = currency;
        this.accounts = accounts;
        this.recentTransactions = recentTransactions;
    }

    public BigDecimal getTotalBalance() { return totalBalance; }
    public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<AccountResponse> getAccounts() { return accounts; }
    public void setAccounts(List<AccountResponse> accounts) { this.accounts = accounts; }

    public List<TransactionResponse> getRecentTransactions() { return recentTransactions; }
    public void setRecentTransactions(List<TransactionResponse> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
