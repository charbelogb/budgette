package com.budgette.backend.infrastructure.persistence.mapper;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Country;
import com.budgette.backend.domain.model.Operator;
import com.budgette.backend.infrastructure.persistence.entity.AccountEntity;
import com.budgette.backend.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toDomain(AccountEntity entity) {
        if (entity == null) return null;
        return new Account(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                Operator.valueOf(entity.getOperator()),
                Country.valueOf(entity.getCountry()),
                entity.getPhoneNumber(),
                entity.getAccountId(),
                entity.getBalance(),
                entity.isActive()
        );
    }

    public AccountEntity toEntity(Account account, UserEntity userEntity) {
        if (account == null) return null;
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId());
        entity.setUser(userEntity);
        entity.setOperator(account.getOperator().name());
        entity.setCountry(account.getCountry().name());
        entity.setPhoneNumber(account.getPhoneNumber());
        entity.setAccountId(account.getAccountId());
        entity.setBalance(account.getBalance());
        entity.setActive(account.isActive());
        return entity;
    }
}
