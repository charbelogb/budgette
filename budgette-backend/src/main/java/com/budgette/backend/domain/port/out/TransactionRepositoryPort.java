package com.budgette.backend.domain.port.out;

import com.budgette.backend.domain.model.Transaction;

import java.util.List;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);

    List<Transaction> saveAll(List<Transaction> transactions);

    List<Transaction> findByAccountId(String accountId);

    List<Transaction> findByAccountIdOrderByDateDesc(String accountId);

    boolean existsByExternalId(String externalId);
}
