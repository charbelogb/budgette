package com.budgette.backend.infrastructure.persistence.adapter;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.infrastructure.persistence.mapper.AccountMapper;
import com.budgette.backend.infrastructure.persistence.repository.AccountJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;
    private final AccountMapper mapper;

    public AccountRepositoryAdapter(AccountJpaRepository jpaRepository, AccountMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Account save(Account account) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(account)));
    }

    @Override
    public Optional<Account> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Account> findByUserId(String userId) {
        return jpaRepository.findByUser_Id(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByUserIdAndPhoneNumber(String userId, String phoneNumber) {
        return jpaRepository.existsByUser_IdAndPhoneNumber(userId, phoneNumber);
    }
}
