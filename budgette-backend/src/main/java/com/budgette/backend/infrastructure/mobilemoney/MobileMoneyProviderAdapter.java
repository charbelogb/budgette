package com.budgette.backend.infrastructure.mobilemoney;

import com.budgette.backend.domain.model.Operator;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderBalanceResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderTransactionResponse;
import com.budgette.backend.infrastructure.mobilemoney.mapper.ProviderTransactionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class MobileMoneyProviderAdapter implements MobileMoneyProviderPort {

    private final WebClient.Builder webClientBuilder;
    private final ProviderTransactionMapper transactionMapper;

    @Value("${providers.mtn.base-url}")
    private String mtnBaseUrl;

    @Value("${providers.moov.base-url}")
    private String moovBaseUrl;

    public MobileMoneyProviderAdapter(WebClient.Builder webClientBuilder,
                                      ProviderTransactionMapper transactionMapper) {
        this.webClientBuilder = webClientBuilder;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Balance getBalance(Operator operator, String accountId) {
        String baseUrl = resolveBaseUrl(operator);
        ProviderBalanceResponse response = webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri("/accounts/{accountId}/balance", accountId)
                .retrieve()
                .bodyToMono(ProviderBalanceResponse.class)
                .block();

        if (response == null) {
            throw new RuntimeException("Impossible de récupérer le solde pour " + operator + " / " + accountId);
        }
        return new Balance(response.getAccountId(), response.getBalance(), response.getCurrency(),
                response.getTimestamp());
    }

    @Override
    public List<Transaction> getTransactions(Operator operator, String accountId,
                                             LocalDateTime from, LocalDateTime to) {
        String baseUrl = resolveBaseUrl(operator);
        ProviderTransactionResponse[] responses = webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri("/accounts/{accountId}/transactions", accountId)
                .retrieve()
                .bodyToMono(ProviderTransactionResponse[].class)
                .block();

        if (responses == null) return List.of();

        return Arrays.stream(responses)
                .map(r -> transactionMapper.toDomain(r, accountId))
                .toList();
    }

    @Override
    public AccountInfo getAccountInfo(Operator operator, String accountId) {
        String baseUrl = resolveBaseUrl(operator);

        com.budgette.backend.infrastructure.mobilemoney.dto.ProviderAccountInfoResponse response =
                webClientBuilder.baseUrl(baseUrl).build()
                        .get()
                        .uri("/accounts/{accountId}/info", accountId)
                        .retrieve()
                        .bodyToMono(com.budgette.backend.infrastructure.mobilemoney.dto.ProviderAccountInfoResponse.class)
                        .block();

        if (response == null) {
            throw new RuntimeException("Impossible de récupérer les infos pour " + operator + " / " + accountId);
        }
        return new AccountInfo(response.getAccountId(), response.getPhoneNumber(),
                response.getFirstName(), response.getLastName(),
                response.getOperator(), response.getCountry(), response.getStatus());
    }

    private String resolveBaseUrl(Operator operator) {
        return switch (operator) {
            case MTN -> mtnBaseUrl;
            case MOOV -> moovBaseUrl;
        };
    }
}
