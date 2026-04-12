package com.budgette.backend.infrastructure.web.mapper;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.in.GetDashboardUseCase;
import com.budgette.backend.infrastructure.web.dto.response.AccountResponse;
import com.budgette.backend.infrastructure.web.dto.response.DashboardResponse;
import com.budgette.backend.infrastructure.web.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebMapper {

    public AccountResponse toAccountResponse(Account account) {
        if (account == null) return null;
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setUserId(account.getUserId());
        response.setOperator(account.getOperator());
        response.setCountry(account.getCountry());
        response.setPhoneNumber(account.getPhoneNumber());
        response.setAccountId(account.getAccountId());
        response.setBalance(account.getBalance());
        response.setActive(account.isActive());
        return response;
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        if (transaction == null) return null;
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAccountId(transaction.getAccountId());
        response.setType(transaction.getType());
        response.setAmount(transaction.getAmount());
        response.setFees(transaction.getFees());
        response.setBalanceAfter(transaction.getBalanceAfter());
        response.setCounterpartyName(transaction.getCounterpartyName());
        response.setCounterpartyPhone(transaction.getCounterpartyPhone());
        response.setDescription(transaction.getDescription());
        response.setDate(transaction.getDate());
        response.setExternalId(transaction.getExternalId());
        return response;
    }

    public DashboardResponse toDashboardResponse(GetDashboardUseCase.DashboardResult result) {
        if (result == null) return null;
        DashboardResponse response = new DashboardResponse();
        response.setUserId(result.userId());
        response.setFullName(result.fullName());
        response.setTotalBalance(result.totalBalance());
        response.setCurrency("FCFA");

        List<AccountResponse> accountResponses = result.accounts().stream()
                .map(this::toAccountResponse)
                .collect(Collectors.toList());
        response.setAccounts(accountResponses);

        List<TransactionResponse> transactionResponses = result.recentTransactions().stream()
                .map(this::toTransactionResponse)
                .collect(Collectors.toList());
        response.setRecentTransactions(transactionResponses);

        return response;
    }
}
