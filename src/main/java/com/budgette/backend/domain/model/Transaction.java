package com.budgette.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
