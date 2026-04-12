package com.budgette.backend.infrastructure.mobilemoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record ProviderTransactionResponse(
        @JsonProperty("id") String id,
        @JsonProperty("type") String type,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("fees") BigDecimal fees,
        @JsonProperty("balanceAfter") BigDecimal balanceAfter,
        @JsonProperty("counterpartyName") String counterpartyName,
        @JsonProperty("counterpartyPhone") String counterpartyPhone,
        @JsonProperty("description") String description,
        @JsonProperty("date") String date
) {}
