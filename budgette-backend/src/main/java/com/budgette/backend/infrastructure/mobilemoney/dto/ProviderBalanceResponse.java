package com.budgette.backend.infrastructure.mobilemoney.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProviderBalanceResponse {

    private String accountId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime timestamp;

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
