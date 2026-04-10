package com.budgette.backend.infrastructure.config;

import com.budgette.backend.domain.port.out.AccountRepositoryPort;
import com.budgette.backend.domain.port.out.MobileMoneyProviderPort;
import com.budgette.backend.domain.port.out.TransactionRepositoryPort;
import com.budgette.backend.domain.port.out.UserRepositoryPort;
import com.budgette.backend.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RegisterUserService.PasswordEncoder domainPasswordEncoder(PasswordEncoder springPasswordEncoder) {
        return springPasswordEncoder::encode;
    }

    @Bean
    public LoginService.PasswordVerifier domainPasswordVerifier(PasswordEncoder springPasswordEncoder) {
        return springPasswordEncoder::matches;
    }

    @Bean
    public LoginService.TokenGenerator domainTokenGenerator(
            com.budgette.backend.infrastructure.security.JwtService jwtService) {
        return jwtService::generateToken;
    }

    @Bean
    public com.budgette.backend.domain.port.in.RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepository,
            RegisterUserService.PasswordEncoder domainPasswordEncoder) {
        return new RegisterUserService(userRepository, domainPasswordEncoder);
    }

    @Bean
    public com.budgette.backend.domain.port.in.LoginUseCase loginUseCase(
            UserRepositoryPort userRepository,
            LoginService.PasswordVerifier domainPasswordVerifier,
            LoginService.TokenGenerator domainTokenGenerator) {
        return new LoginService(userRepository, domainPasswordVerifier, domainTokenGenerator);
    }

    @Bean
    public com.budgette.backend.domain.port.in.AddAccountUseCase addAccountUseCase(
            AccountRepositoryPort accountRepository,
            MobileMoneyProviderPort mobileMoneyProvider) {
        return new AddAccountService(accountRepository, mobileMoneyProvider);
    }

    @Bean
    public com.budgette.backend.domain.port.in.SyncTransactionsUseCase syncTransactionsUseCase(
            AccountRepositoryPort accountRepository,
            TransactionRepositoryPort transactionRepository,
            MobileMoneyProviderPort mobileMoneyProvider) {
        return new SyncTransactionsService(accountRepository, transactionRepository, mobileMoneyProvider);
    }

    @Bean
    public com.budgette.backend.domain.port.in.GetDashboardUseCase getDashboardUseCase(
            AccountRepositoryPort accountRepository,
            TransactionRepositoryPort transactionRepository) {
        return new GetDashboardService(accountRepository, transactionRepository);
    }
}
