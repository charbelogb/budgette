package com.budgette.backend.infrastructure.web.dto.response;

import com.budgette.backend.domain.model.Country;
import com.budgette.backend.domain.model.Operator;

import java.math.BigDecimal;

public record AccountResponse(
    String id,
    String userId,
    Operator operator,
    Country country,
    String phoneNumber,
    String accountId,
    BigDecimal balance,
    boolean active
) {}
