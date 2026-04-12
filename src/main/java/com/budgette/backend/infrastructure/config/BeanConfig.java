package com.budgette.backend.infrastructure.config;

import com.budgette.backend.domain.port.in.*;
import com.budgette.backend.domain.port.out.*;
import com.budgette.backend.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepositoryPort userRepositoryPort,
                                                    BCryptPasswordEncoder passwordEncoder) {
        return new RegisterUserService(userRepositoryPort, passwordEncoder::encode);
    }

    @Bean
    public LoginUseCase loginUseCase(UserRepositoryPort userRepositoryPort,
                                     BCryptPasswordEncoder passwordEncoder,
                                     com.budgette.backend.infrastructure.security.JwtService jwtService) {
        return new LoginService(
                userRepositoryPort,
                passwordEncoder::matches,
                jwtService::generateToken
        );
    }

    @Bean
    public AddAccountUseCase addAccountUseCase(AccountRepositoryPort accountRepositoryPort,
                                               UserRepositoryPort userRepositoryPort,
                                               MobileMoneyProviderPort mobileMoneyProviderPort) {
        return new AddAccountService(accountRepositoryPort, userRepositoryPort, mobileMoneyProviderPort);
    }

    @Bean
    public SyncTransactionsUseCase syncTransactionsUseCase(AccountRepositoryPort accountRepositoryPort,
                                                           TransactionRepositoryPort transactionRepositoryPort,
                                                           MobileMoneyProviderPort mobileMoneyProviderPort) {
        return new SyncTransactionsService(accountRepositoryPort, transactionRepositoryPort, mobileMoneyProviderPort);
    }

    @Bean
    public GetDashboardUseCase getDashboardUseCase(UserRepositoryPort userRepositoryPort,
                                                   AccountRepositoryPort accountRepositoryPort,
                                                   TransactionRepositoryPort transactionRepositoryPort) {
        return new GetDashboardService(userRepositoryPort, accountRepositoryPort, transactionRepositoryPort);
    }
}
