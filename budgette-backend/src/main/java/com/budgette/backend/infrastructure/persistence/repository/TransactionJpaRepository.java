package com.budgette.backend.infrastructure.persistence.repository;

import com.budgette.backend.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findByAccount_Id(String accountId);

    List<TransactionEntity> findByAccount_IdOrderByDateDesc(String accountId);

    boolean existsByExternalId(String externalId);
}
