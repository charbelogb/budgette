package com.budgette.backend.infrastructure.mobilemoney;

import com.budgette.backend.domain.model.Operator;
import com.budgette.backend.domain.model.Transaction;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderAccountInfoResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderBalanceResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderTransactionResponse;
import com.budgette.backend.infrastructure.mobilemoney.mapper.ProviderTransactionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MobileMoneyProviderAdapter implements MobileMoneyProviderPort {

    private final WebClient mtnWebClient;
    private final WebClient moovWebClient;
    private final ProviderTransactionMapper transactionMapper;

    public MobileMoneyProviderAdapter(
            @Value("${providers.mtn.base-url}") String mtnBaseUrl,
            @Value("${providers.moov.base-url}") String moovBaseUrl,
            WebClient.Builder webClientBuilder,
            ProviderTransactionMapper transactionMapper) {
        this.mtnWebClient = webClientBuilder.baseUrl(mtnBaseUrl).build();
        this.moovWebClient = webClientBuilder.baseUrl(moovBaseUrl).build();
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Balance getBalance(Operator operator, String accountId) {
        ProviderBalanceResponse response = getWebClient(operator)
                .get()
                .uri("/accounts/{accountId}/balance", accountId)
                .retrieve()
                .bodyToMono(ProviderBalanceResponse.class)
                .block();

        if (response == null) {
            return new Balance(accountId, java.math.BigDecimal.ZERO, "FCFA");
        }
        return new Balance(response.getAccountId(), response.getBalance(), response.getCurrency());
    }

    @Override
    public List<Transaction> getTransactions(Operator operator, String accountId,
                                             LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        List<ProviderTransactionResponse> responses = getWebClient(operator)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts/{accountId}/transactions")
                        .queryParam("from", from.format(formatter))
                        .queryParam("to", to.format(formatter))
                        .build(accountId))
                .retrieve()
                .bodyToFlux(ProviderTransactionResponse.class)
                .collectList()
                .block();

        if (responses == null) return List.of();

        return responses.stream()
                .map(r -> transactionMapper.toDomain(r, accountId))
                .collect(Collectors.toList());
    }

    @Override
    public AccountInfo getAccountInfo(Operator operator, String accountId) {
        ProviderAccountInfoResponse response = getWebClient(operator)
                .get()
                .uri("/accounts/{accountId}", accountId)
                .retrieve()
                .bodyToMono(ProviderAccountInfoResponse.class)
                .block();

        if (response == null) {
            return new AccountInfo(accountId, null, null, false);
        }
        return new AccountInfo(
                response.getAccountId(),
                response.getPhoneNumber(),
                response.getHolderName(),
                response.isActive()
        );
    }

    private WebClient getWebClient(Operator operator) {
        return operator == Operator.MTN ? mtnWebClient : moovWebClient;
    }
}
