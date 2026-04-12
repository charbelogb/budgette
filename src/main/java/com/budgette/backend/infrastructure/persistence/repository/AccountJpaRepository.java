package com.budgette.backend.infrastructure.persistence.repository;

import com.budgette.backend.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

    List<AccountEntity> findByUserId(String userId);

    Optional<AccountEntity> findByAccountId(String accountId);

    boolean existsByAccountId(String accountId);
}
