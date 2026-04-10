package com.budgette.backend.domain.port.out;

import com.budgette.backend.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {

    Account save(Account account);

    Optional<Account> findById(String id);

    List<Account> findByUserId(String userId);

    boolean existsByUserIdAndPhoneNumber(String userId, String phoneNumber);
}
