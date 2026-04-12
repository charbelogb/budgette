package com.budgette.backend.application.dto.response;

import com.budgette.backend.domain.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
    String id,
    String accountId,
    TransactionType type,
    BigDecimal amount,
    BigDecimal fees,
    BigDecimal balanceAfter,
    String counterpartyName,
    String counterpartyPhone,
    String description,
    LocalDateTime date,
    String externalId
) {}
