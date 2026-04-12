package com.budgette.backend.infrastructure.mobilemoney;

import com.budgette.backend.domain.model.Operator;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.infrastructure.mobilemoney.client.MTNFeignClient;
import com.budgette.backend.infrastructure.mobilemoney.client.MoovFeignClient;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderAccountInfoResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderBalanceResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderTransactionResponse;
import com.budgette.backend.infrastructure.mobilemoney.mapper.ProviderTransactionMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MobileMoneyProviderAdapter implements MobileMoneyProviderPort {

    private final MTNFeignClient mtnClient;
    private final MoovFeignClient moovClient;
    private final ProviderTransactionMapper transactionMapper;

    public MobileMoneyProviderAdapter(MTNFeignClient mtnClient,
                                      MoovFeignClient moovClient,
                                      ProviderTransactionMapper transactionMapper) {
        this.mtnClient = mtnClient;
        this.moovClient = moovClient;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Balance getBalance(Operator operator, String accountId) {
        ProviderBalanceResponse response = operator == Operator.MTN
                ? mtnClient.getBalance(accountId)
                : moovClient.getBalance(accountId);

        if (response == null) {
            return new Balance(accountId, BigDecimal.ZERO, "FCFA");
        }
        return new Balance(response.accountId(), response.balance(), response.currency());
    }

    @Override
    public List<Transaction> getTransactions(Operator operator, String accountId,
                                             LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String fromStr = from.format(formatter);
        String toStr = to.format(formatter);

        List<ProviderTransactionResponse> responses = operator == Operator.MTN
                ? mtnClient.getTransactions(accountId, fromStr, toStr)
                : moovClient.getTransactions(accountId, fromStr, toStr);

        if (responses == null) return List.of();

        return responses.stream()
                .map(r -> transactionMapper.toDomain(r, accountId))
                .collect(Collectors.toList());
    }

    @Override
    public AccountInfo getAccountInfo(Operator operator, String accountId) {
        ProviderAccountInfoResponse response = operator == Operator.MTN
                ? mtnClient.getAccountInfo(accountId)
                : moovClient.getAccountInfo(accountId);

        if (response == null) {
            return new AccountInfo(accountId, null, null, false);
        }
        return new AccountInfo(
                response.accountId(),
                response.phoneNumber(),
                response.holderName(),
                response.active()
        );
    }
}
