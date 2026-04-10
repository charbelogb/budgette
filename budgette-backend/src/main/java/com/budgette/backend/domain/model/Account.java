package com.budgette.backend.domain.model;

import java.math.BigDecimal;

public class Account {

    private String id;
    private String userId;
    private Operator operator;
    private Country country;
    private String phoneNumber;
    private String accountId;
    private BigDecimal balance;
    private boolean active;

    public Account() {}

    public Account(String id, String userId, Operator operator, Country country,
                   String phoneNumber, String accountId) {
        this.id = id;
        this.userId = userId;
        this.operator = operator;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.accountId = accountId;
        this.balance = BigDecimal.ZERO;
        this.active = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
