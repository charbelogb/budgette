package com.budgette.backend.domain.port.out;

import com.budgette.backend.domain.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);

    List<Transaction> saveAll(List<Transaction> transactions);

    Optional<Transaction> findById(String id);

    List<Transaction> findByAccountId(String accountId);

    List<Transaction> findByAccountIdAndDateBetween(String accountId, LocalDateTime from, LocalDateTime to);

    boolean existsByExternalId(String externalId);
}
