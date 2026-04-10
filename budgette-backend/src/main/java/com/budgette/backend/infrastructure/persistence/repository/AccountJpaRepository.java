package com.budgette.backend.infrastructure.persistence.repository;

import com.budgette.backend.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

    List<AccountEntity> findByUser_Id(String userId);

    boolean existsByUser_IdAndPhoneNumber(String userId, String phoneNumber);
}
