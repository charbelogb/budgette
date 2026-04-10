package com.budgette.backend.domain.service;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.port.in.AddAccountUseCase;
import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;

import java.util.UUID;

public class AddAccountService implements AddAccountUseCase {

    private final AccountRepositoryPort accountRepository;
    private final MobileMoneyProviderPort mobileMoneyProvider;

    public AddAccountService(AccountRepositoryPort accountRepository,
                             MobileMoneyProviderPort mobileMoneyProvider) {
        this.accountRepository = accountRepository;
        this.mobileMoneyProvider = mobileMoneyProvider;
    }

    @Override
    public Account addAccount(AddAccountCommand command) {
        if (accountRepository.existsByUserIdAndPhoneNumber(command.userId(), command.phoneNumber())) {
            throw new IllegalArgumentException("Ce numéro est déjà associé à votre compte.");
        }

        String accountId = command.phoneNumber().replaceAll("[^0-9]", "");

        MobileMoneyProviderPort.AccountInfo info =
                mobileMoneyProvider.getAccountInfo(command.operator(), accountId);

        Account account = new Account(
                UUID.randomUUID().toString(),
                command.userId(),
                command.operator(),
                command.country(),
                command.phoneNumber(),
                accountId
        );

        MobileMoneyProviderPort.Balance balance =
                mobileMoneyProvider.getBalance(command.operator(), accountId);
        account.setBalance(balance.amount());

        return accountRepository.save(account);
    }
}
