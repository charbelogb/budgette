package com.budgette.backend.infrastructure.persistence.repository;

import com.budgette.backend.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findByAccountId(String accountId);

    List<TransactionEntity> findByAccountIdAndDateBetween(String accountId, LocalDateTime from, LocalDateTime to);

    boolean existsByExternalId(String externalId);
}
