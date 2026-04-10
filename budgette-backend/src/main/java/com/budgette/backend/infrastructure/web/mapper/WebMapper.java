package com.budgette.backend.infrastructure.web.mapper;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.infrastructure.web.dto.response.AccountResponse;
import com.budgette.backend.infrastructure.web.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class WebMapper {

    public AccountResponse toAccountResponse(Account account) {
        if (account == null) return null;
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setOperator(account.getOperator().name());
        response.setOperatorDisplayName(account.getOperator().getDisplayName());
        response.setCountry(account.getCountry().name());
        response.setPhoneNumber(account.getPhoneNumber());
        response.setBalance(account.getBalance());
        response.setCurrency(account.getCountry().getCurrency());
        response.setActive(account.isActive());
        return response;
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        if (transaction == null) return null;
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAccountId(transaction.getAccountId());
        response.setType(transaction.getType().name());
        response.setAmount(transaction.getAmount());
        response.setFees(transaction.getFees());
        response.setBalanceAfter(transaction.getBalanceAfter());
        response.setCounterpartyName(transaction.getCounterpartyName());
        response.setCounterpartyPhone(transaction.getCounterpartyPhone());
        response.setDescription(transaction.getDescription());
        response.setDate(transaction.getDate());
        return response;
    }
}
