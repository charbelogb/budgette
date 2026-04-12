package com.budgette.backend.infrastructure.persistence.adapter;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.infrastructure.persistence.entity.UserEntity;
import com.budgette.backend.infrastructure.persistence.mapper.AccountMapper;
import com.budgette.backend.infrastructure.persistence.repository.AccountJpaRepository;
import com.budgette.backend.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository accountJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AccountMapper accountMapper;

    public AccountRepositoryAdapter(AccountJpaRepository accountJpaRepository,
                                    UserJpaRepository userJpaRepository,
                                    AccountMapper accountMapper) {
        this.accountJpaRepository = accountJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account save(Account account) {
        UserEntity userEntity = userJpaRepository.findById(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable : " + account.getUserId()));
        return accountMapper.toDomain(accountJpaRepository.save(accountMapper.toEntity(account, userEntity)));
    }

    @Override
    public Optional<Account> findById(String id) {
        return accountJpaRepository.findById(id).map(accountMapper::toDomain);
    }

    @Override
    public List<Account> findByUserId(String userId) {
        return accountJpaRepository.findByUserId(userId).stream()
                .map(accountMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> findByAccountId(String accountId) {
        return accountJpaRepository.findByAccountId(accountId).map(accountMapper::toDomain);
    }

    @Override
    public boolean existsByAccountId(String accountId) {
        return accountJpaRepository.existsByAccountId(accountId);
    }
}
