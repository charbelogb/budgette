package com.budgette.backend.infrastructure.mobilemoney.mapper;

import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.model.TransactionType;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderTransactionResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class ProviderTransactionMapper {

    public Transaction toDomain(ProviderTransactionResponse response, String accountId) {
        if (response == null) return null;

        LocalDateTime date = null;
        if (response.date() != null) {
            try {
                date = LocalDateTime.parse(response.date(), DateTimeFormatter.ISO_DATE_TIME);
            } catch (Exception e) {
                date = LocalDateTime.now();
            }
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(response.type() != null ? response.type().toUpperCase() : "PAYMENT");
        } catch (IllegalArgumentException e) {
            type = TransactionType.PAYMENT;
        }

        return new Transaction(
                UUID.randomUUID().toString(),
                accountId,
                type,
                response.amount(),
                response.fees(),
                response.balanceAfter(),
                response.counterpartyName(),
                response.counterpartyPhone(),
                response.description(),
                date,
                response.id()
        );
    }
}
