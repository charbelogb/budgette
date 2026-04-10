package com.budgette.backend.infrastructure.web.dto.response;

import java.math.BigDecimal;

public class AccountResponse {

    private String id;
    private String operator;
    private String operatorDisplayName;
    private String country;
    private String phoneNumber;
    private BigDecimal balance;
    private String currency;
    private boolean active;

    public AccountResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getOperatorDisplayName() { return operatorDisplayName; }
    public void setOperatorDisplayName(String operatorDisplayName) { this.operatorDisplayName = operatorDisplayName; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
