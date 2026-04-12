package com.budgette.backend.infrastructure.mobilemoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProviderAccountInfoResponse(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("phoneNumber") String phoneNumber,
        @JsonProperty("holderName") String holderName,
        @JsonProperty("active") boolean active
) {}
