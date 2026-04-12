package com.budgette.backend.application.dto.request;

import com.budgette.backend.domain.model.Country;
import com.budgette.backend.domain.model.Operator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddAccountRequest(
    @NotNull(message = "L'opérateur est obligatoire")
    Operator operator,

    @NotNull(message = "Le pays est obligatoire")
    Country country,

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    String phoneNumber,

    @NotBlank(message = "L'identifiant du compte est obligatoire")
    String accountId
) {}
