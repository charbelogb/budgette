package com.budgette.simulator.dto;

public class AccountInfoDTO {

    private String accountId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String operator;
    private String country;
    private String status;
    private String kycLevel;

    public AccountInfoDTO() {}

    public AccountInfoDTO(String accountId, String phoneNumber, String firstName, String lastName,
                          String operator, String country, String status, String kycLevel) {
        this.accountId = accountId;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.operator = operator;
        this.country = country;
        this.status = status;
        this.kycLevel = kycLevel;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getKycLevel() { return kycLevel; }
    public void setKycLevel(String kycLevel) { this.kycLevel = kycLevel; }
}
