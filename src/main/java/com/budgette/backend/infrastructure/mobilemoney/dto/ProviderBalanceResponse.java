package com.budgette.backend.infrastructure.mobilemoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record ProviderBalanceResponse(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("balance") BigDecimal balance,
        @JsonProperty("currency") String currency
) {}
