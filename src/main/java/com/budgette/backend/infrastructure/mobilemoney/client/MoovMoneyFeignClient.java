package com.budgette.backend.infrastructure.mobilemoney.client;

import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderAccountInfoResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderBalanceResponse;
import com.budgette.backend.infrastructure.mobilemoney.dto.ProviderTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "moov-client", url = "${providers.moov.base-url}")
public interface MoovMoneyFeignClient {

    @GetMapping("/accounts/{accountId}/balance")
    ProviderBalanceResponse getBalance(@PathVariable("accountId") String accountId);

    @GetMapping("/accounts/{accountId}/transactions")
    List<ProviderTransactionResponse> getTransactions(
            @PathVariable("accountId") String accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );

    @GetMapping("/accounts/{accountId}")
    ProviderAccountInfoResponse getAccountInfo(@PathVariable("accountId") String accountId);
}
