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
        Account account = new Account();
        account.setId(entity.getId());
        account.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        account.setOperator(Operator.valueOf(entity.getOperator()));
        account.setCountry(Country.valueOf(entity.getCountry()));
        account.setPhoneNumber(entity.getPhoneNumber());
        account.setAccountId(entity.getAccountId());
        account.setBalance(entity.getBalance());
        account.setActive(entity.isActive());
        return account;
    }

    public AccountEntity toEntity(Account account) {
        if (account == null) return null;
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(account.getUserId());
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
