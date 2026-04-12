package com.budgette.backend.application.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {

    private String userId;
    private String fullName;
    private BigDecimal totalBalance;
    private String currency;
    private List<AccountResponse> accounts;
    private List<TransactionResponse> recentTransactions;

    public DashboardResponse() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public BigDecimal getTotalBalance() { return totalBalance; }
    public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<AccountResponse> getAccounts() { return accounts; }
    public void setAccounts(List<AccountResponse> accounts) { this.accounts = accounts; }

    public List<TransactionResponse> getRecentTransactions() { return recentTransactions; }
    public void setRecentTransactions(List<TransactionResponse> recentTransactions) { this.recentTransactions = recentTransactions; }
}
