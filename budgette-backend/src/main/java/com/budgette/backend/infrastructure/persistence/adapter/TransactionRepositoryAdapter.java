package com.budgette.backend.infrastructure.persistence.adapter;

import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.model.TransactionType;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;
import com.budgette.backend.infrastructure.persistence.entity.AccountEntity;
import com.budgette.backend.infrastructure.persistence.entity.TransactionEntity;
import com.budgette.backend.infrastructure.persistence.repository.TransactionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;

    public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return toDomain(jpaRepository.save(toEntity(transaction)));
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> transactions) {
        return jpaRepository.saveAll(transactions.stream().map(this::toEntity).toList())
                .stream().map(this::toDomain).toList();
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        return jpaRepository.findByAccount_Id(accountId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Transaction> findByAccountIdOrderByDateDesc(String accountId) {
        return jpaRepository.findByAccount_IdOrderByDateDesc(accountId).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public boolean existsByExternalId(String externalId) {
        return jpaRepository.existsByExternalId(externalId);
    }

    private Transaction toDomain(TransactionEntity entity) {
        Transaction t = new Transaction();
        t.setId(entity.getId());
        t.setAccountId(entity.getAccount() != null ? entity.getAccount().getId() : null);
        t.setType(TransactionType.valueOf(entity.getType()));
        t.setAmount(entity.getAmount());
        t.setFees(entity.getFees());
        t.setBalanceAfter(entity.getBalanceAfter());
        t.setCounterpartyName(entity.getCounterpartyName());
        t.setCounterpartyPhone(entity.getCounterpartyPhone());
        t.setDescription(entity.getDescription());
        t.setDate(entity.getDate());
        t.setExternalId(entity.getExternalId());
        return t;
    }

    private TransactionEntity toEntity(Transaction t) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(t.getId());

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(t.getAccountId());
        entity.setAccount(accountEntity);

        entity.setType(t.getType().name());
        entity.setAmount(t.getAmount());
        entity.setFees(t.getFees());
        entity.setBalanceAfter(t.getBalanceAfter());
        entity.setCounterpartyName(t.getCounterpartyName());
        entity.setCounterpartyPhone(t.getCounterpartyPhone());
        entity.setDescription(t.getDescription());
        entity.setDate(t.getDate());
        entity.setExternalId(t.getExternalId());
        return entity;
    }
}
