package com.budgette.backend.infrastructure.persistence.adapter;

import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.model.TransactionType;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;
import com.budgette.backend.infrastructure.persistence.entity.TransactionEntity;
import com.budgette.backend.infrastructure.persistence.repository.TransactionJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepositoryAdapter(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return toDomain(transactionJpaRepository.save(toEntity(transaction)));
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> transactions) {
        List<TransactionEntity> entities = transactions.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return transactionJpaRepository.saveAll(entities).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return transactionJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        return transactionJpaRepository.findByAccountId(accountId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByAccountIdAndDateBetween(String accountId, LocalDateTime from, LocalDateTime to) {
        return transactionJpaRepository.findByAccountIdAndDateBetween(accountId, from, to).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByExternalId(String externalId) {
        return transactionJpaRepository.existsByExternalId(externalId);
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getAccountId(),
                TransactionType.valueOf(entity.getType()),
                entity.getAmount(),
                entity.getFees(),
                entity.getBalanceAfter(),
                entity.getCounterpartyName(),
                entity.getCounterpartyPhone(),
                entity.getDescription(),
                entity.getDate(),
                entity.getExternalId()
        );
    }

    private TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId() != null ? transaction.getId() : UUID.randomUUID().toString());
        entity.setAccountId(transaction.getAccountId());
        entity.setType(transaction.getType().name());
        entity.setAmount(transaction.getAmount());
        entity.setFees(transaction.getFees());
        entity.setBalanceAfter(transaction.getBalanceAfter());
        entity.setCounterpartyName(transaction.getCounterpartyName());
        entity.setCounterpartyPhone(transaction.getCounterpartyPhone());
        entity.setDescription(transaction.getDescription());
        entity.setDate(transaction.getDate());
        entity.setExternalId(transaction.getExternalId());
        return entity;
    }
}
