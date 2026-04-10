package com.budgette.backend.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddAccountRequest {

    @NotNull(message = "L'opérateur est requis")
    private String operator;

    @NotNull(message = "Le pays est requis")
    private String country;

    @NotBlank(message = "Le numéro de téléphone est requis")
    private String phoneNumber;

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
