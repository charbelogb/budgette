package com.budgette.backend.domain.port.in;

import com.budgette.backend.domain.model.Transaction;

import java.util.List;

public interface SyncTransactionsUseCase {

    List<Transaction> syncTransactions(SyncCommand command);

    record SyncCommand(
            String userId,
            String accountId
    ) {}
}
