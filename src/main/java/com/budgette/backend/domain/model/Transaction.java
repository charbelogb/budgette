package com.budgette.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private String id;
    private String accountId;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal fees;
    private BigDecimal balanceAfter;
    private String counterpartyName;
    private String counterpartyPhone;
    private String description;
    private LocalDateTime date;
    private String externalId;
}
