package com.budgette.backend.application.dto.request;

import com.budgette.backend.domain.model.Country;
import com.budgette.backend.domain.model.Operator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddAccountRequest {

    @NotNull(message = "L'opérateur est obligatoire")
    private Operator operator;

    @NotNull(message = "Le pays est obligatoire")
    private Country country;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String phoneNumber;

    @NotBlank(message = "L'identifiant du compte est obligatoire")
    private String accountId;

    public AddAccountRequest() {}

    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
}
