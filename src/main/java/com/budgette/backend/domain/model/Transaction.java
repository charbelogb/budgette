package com.budgette.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String id;
    private String accountId;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal fees;
    private BigDecimal balanceAfter;
    private String counterpartyName;
    private String counterpartyPhone;
    private String description;
    private LocalDateTime date;
    private String externalId;

    public Transaction() {}

    public Transaction(String id, String accountId, TransactionType type, BigDecimal amount,
                       BigDecimal fees, BigDecimal balanceAfter, String counterpartyName,
                       String counterpartyPhone, String description, LocalDateTime date,
                       String externalId) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.fees = fees;
        this.balanceAfter = balanceAfter;
        this.counterpartyName = counterpartyName;
        this.counterpartyPhone = counterpartyPhone;
        this.description = description;
        this.date = date;
        this.externalId = externalId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getFees() { return fees; }
    public void setFees(BigDecimal fees) { this.fees = fees; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }

    public String getCounterpartyName() { return counterpartyName; }
    public void setCounterpartyName(String counterpartyName) { this.counterpartyName = counterpartyName; }

    public String getCounterpartyPhone() { return counterpartyPhone; }
    public void setCounterpartyPhone(String counterpartyPhone) { this.counterpartyPhone = counterpartyPhone; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }
}
