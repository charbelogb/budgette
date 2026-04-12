package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.port.in.AddAccountUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.domain.port.out.UserRepositoryPort;

import java.math.BigDecimal;
import java.util.UUID;

public class AddAccountService implements AddAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final MobileMoneyProviderPort mobileMoneyProviderPort;

    public AddAccountService(AccountRepositoryPort accountRepositoryPort,
                             UserRepositoryPort userRepositoryPort,
                             MobileMoneyProviderPort mobileMoneyProviderPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.mobileMoneyProviderPort = mobileMoneyProviderPort;
    }

    @Override
    public Account addAccount(AddAccountCommand command) {
        userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable : " + command.userId()));

        if (accountRepositoryPort.existsByAccountId(command.accountId())) {
            throw new IllegalArgumentException("Ce compte Mobile Money est déjà enregistré.");
        }

        MobileMoneyProviderPort.AccountInfo accountInfo =
                mobileMoneyProviderPort.getAccountInfo(command.operator(), command.accountId());

        MobileMoneyProviderPort.Balance balance =
                mobileMoneyProviderPort.getBalance(command.operator(), command.accountId());

        Account account = new Account(
                UUID.randomUUID().toString(),
                command.userId(),
                command.operator(),
                command.country(),
                accountInfo.phoneNumber() != null ? accountInfo.phoneNumber() : command.phoneNumber(),
                command.accountId(),
                balance.amount() != null ? balance.amount() : BigDecimal.ZERO,
                accountInfo.active()
        );

        return accountRepositoryPort.save(account);
    }
}
