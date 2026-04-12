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
        return new AccountResponse(
                account.getId(),
                account.getUserId(),
                account.getOperator(),
                account.getCountry(),
                account.getPhoneNumber(),
                account.getAccountId(),
                account.getBalance(),
                account.isActive()
        );
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        if (transaction == null) return null;
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getFees(),
                transaction.getBalanceAfter(),
                transaction.getCounterpartyName(),
                transaction.getCounterpartyPhone(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getExternalId()
        );
    }

    public DashboardResponse toDashboardResponse(GetDashboardUseCase.DashboardResult result) {
        if (result == null) return null;

        List<AccountResponse> accountResponses = result.accounts().stream()
                .map(this::toAccountResponse)
                .collect(Collectors.toList());

        List<TransactionResponse> transactionResponses = result.recentTransactions().stream()
                .map(this::toTransactionResponse)
                .collect(Collectors.toList());

        return new DashboardResponse(
                result.userId(),
                result.fullName(),
                result.totalBalance(),
                "FCFA",
                accountResponses,
                transactionResponses
        );
    }
}
