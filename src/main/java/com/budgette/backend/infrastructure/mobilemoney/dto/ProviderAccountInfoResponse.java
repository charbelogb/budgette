package com.budgette.backend.infrastructure.mobilemoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProviderAccountInfoResponse {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("holderName")
    private String holderName;

    @JsonProperty("active")
    private boolean active;

    public ProviderAccountInfoResponse() {}

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
