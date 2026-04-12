package com.budgette.backend.infrastructure.mobilemoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ProviderTransactionResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("fees")
    private BigDecimal fees;

    @JsonProperty("balanceAfter")
    private BigDecimal balanceAfter;

    @JsonProperty("counterpartyName")
    private String counterpartyName;

    @JsonProperty("counterpartyPhone")
    private String counterpartyPhone;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private String date;

    public ProviderTransactionResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

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

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
