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
        if (response.getDate() != null) {
            try {
                date = LocalDateTime.parse(response.getDate(), DateTimeFormatter.ISO_DATE_TIME);
            } catch (Exception e) {
                date = LocalDateTime.now();
            }
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(response.getType() != null ? response.getType().toUpperCase() : "PAYMENT");
        } catch (IllegalArgumentException e) {
            type = TransactionType.PAYMENT;
        }

        return new Transaction(
                UUID.randomUUID().toString(),
                accountId,
                type,
                response.getAmount(),
                response.getFees(),
                response.getBalanceAfter(),
                response.getCounterpartyName(),
                response.getCounterpartyPhone(),
                response.getDescription(),
                date,
                response.getId()
        );
    }
}
