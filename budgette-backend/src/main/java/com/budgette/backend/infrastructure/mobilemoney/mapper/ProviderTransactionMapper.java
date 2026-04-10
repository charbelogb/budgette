package com.budgette.backend.infrastructure.mobilemoney.mapper;

import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.model.TransactionType;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderTransactionResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProviderTransactionMapper {

    public Transaction toDomain(ProviderTransactionResponse response, String accountId) {
        if (response == null) return null;

        TransactionType type;
        try {
            type = TransactionType.valueOf(response.getType());
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
                response.getDate(),
                response.getId()
        );
    }
}
