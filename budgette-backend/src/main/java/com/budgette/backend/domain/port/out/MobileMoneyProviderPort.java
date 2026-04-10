package com.budgette.backend.domain.port.out;

import com.budgette.backend.domain.model.Operator;
import com.budgette.backend.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface MobileMoneyProviderPort {

    record Balance(String accountId, BigDecimal amount, String currency, LocalDateTime timestamp) {}

    record AccountInfo(String accountId, String phoneNumber, String firstName, String lastName,
                       String operator, String country, String status) {}

    Balance getBalance(Operator operator, String accountId);

    List<Transaction> getTransactions(Operator operator, String accountId,
                                      LocalDateTime from, LocalDateTime to);

    AccountInfo getAccountInfo(Operator operator, String accountId);
}
