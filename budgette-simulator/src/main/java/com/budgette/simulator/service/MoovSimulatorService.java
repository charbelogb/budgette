package com.budgette.simulator.service;

import com.budgette.simulator.dto.AccountInfoDTO;
import com.budgette.simulator.dto.BalanceDTO;
import com.budgette.simulator.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class MoovSimulatorService {

    private static final Random RANDOM = new Random();

    private static final String[] FIRST_NAMES = {
        "Gbènoukpo", "Félicité", "Arnauld", "Nadège", "Crépin", "Pascaline",
        "Jérémie", "Odile", "Anselme", "Romuald", "Scholastique", "Édouard",
        "Hyacinthe", "Immaculée", "Léopold", "Maximin", "Victorine", "Rémi"
    };

    private static final String[] LAST_NAMES = {
        "Hounkpatin", "Agbossou", "Kpèdékpo", "Fagnon", "Koudogbo", "Assogba",
        "Akogbéto", "Tchétchèdji", "Massi", "Sègla", "Padonou", "Houéto",
        "Biaou", "Salifou", "Gnimassoun", "Dèdètché", "Kossou", "Zinzindohoué"
    };

    private static final String[] TRANSACTION_TYPES = {
        "SEND", "RECEIVE", "WITHDRAW", "DEPOSIT", "PAYMENT", "AIRTIME"
    };

    private static final String[] MERCHANT_NAMES = {
        "Boulangerie La Mie Dorée", "Cyber Café Horizon", "SBEE Paiement",
        "SONEB Eau", "Taxi Moto Service", "Hôtel Bénin Marina",
        "Clinique Cotonou Nord", "Librairie Flamboyant"
    };

    public BalanceDTO getBalance(String accountId) {
        simulateLatency();
        BigDecimal balance = BigDecimal.valueOf(3000 + RANDOM.nextInt(297000))
                .setScale(0, RoundingMode.HALF_UP);
        return new BalanceDTO(accountId, balance, "XOF", LocalDateTime.now());
    }

    public List<TransactionDTO> getTransactions(String accountId) {
        simulateLatency();
        int count = 8 + RANDOM.nextInt(23);
        List<TransactionDTO> transactions = new ArrayList<>();
        BigDecimal runningBalance = BigDecimal.valueOf(30000 + RANDOM.nextInt(150000));

        for (int i = 0; i < count; i++) {
            String type = TRANSACTION_TYPES[RANDOM.nextInt(TRANSACTION_TYPES.length)];
            BigDecimal amount = generateAmount(type);
            BigDecimal fees = calculateFees(type, amount);
            String counterpartyName = generateCounterpartyName(type);
            String counterpartyPhone = generatePhoneNumber();

            if (List.of("SEND", "WITHDRAW", "PAYMENT", "AIRTIME").contains(type)) {
                runningBalance = runningBalance.subtract(amount).subtract(fees);
            } else {
                runningBalance = runningBalance.add(amount);
            }
            if (runningBalance.compareTo(BigDecimal.ZERO) < 0) {
                runningBalance = BigDecimal.valueOf(3000);
            }

            transactions.add(new TransactionDTO(
                    "MOOV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                    type,
                    amount,
                    fees,
                    runningBalance,
                    counterpartyName,
                    counterpartyPhone,
                    generateDescription(type, counterpartyName),
                    LocalDateTime.now().minusDays(RANDOM.nextInt(30)).minusHours(RANDOM.nextInt(24)),
                    "SUCCESS"
            ));
        }

        transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        return transactions;
    }

    public AccountInfoDTO getAccountInfo(String accountId) {
        simulateLatency();
        String firstName = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
        return new AccountInfoDTO(
                accountId,
                generatePhoneNumber(),
                firstName,
                lastName,
                "MOOV_MONEY",
                "BJ",
                "ACTIVE",
                "KYC_2"
        );
    }

    private BigDecimal generateAmount(String type) {
        return switch (type) {
            case "SEND" -> BigDecimal.valueOf(500 + RANDOM.nextInt(49500));
            case "RECEIVE" -> BigDecimal.valueOf(500 + RANDOM.nextInt(99500));
            case "WITHDRAW" -> BigDecimal.valueOf(1000 + RANDOM.nextInt(49000));
            case "DEPOSIT" -> BigDecimal.valueOf(1000 + RANDOM.nextInt(99000));
            case "PAYMENT" -> BigDecimal.valueOf(100 + RANDOM.nextInt(9900));
            case "AIRTIME" -> BigDecimal.valueOf(100 + RANDOM.nextInt(900));
            default -> BigDecimal.valueOf(1000);
        };
    }

    private BigDecimal calculateFees(String type, BigDecimal amount) {
        return switch (type) {
            case "SEND" -> amount.multiply(BigDecimal.valueOf(0.012)).setScale(0, RoundingMode.HALF_UP);
            case "WITHDRAW" -> amount.multiply(BigDecimal.valueOf(0.02)).setScale(0, RoundingMode.HALF_UP);
            case "PAYMENT" -> BigDecimal.valueOf(75);
            default -> BigDecimal.ZERO;
        };
    }

    private String generateCounterpartyName(String type) {
        if ("PAYMENT".equals(type)) {
            return MERCHANT_NAMES[RANDOM.nextInt(MERCHANT_NAMES.length)];
        }
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)] + " "
                + LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    private String generatePhoneNumber() {
        return "+22996" + String.format("%07d", RANDOM.nextInt(10000000));
    }

    private String generateDescription(String type, String counterpartyName) {
        return switch (type) {
            case "SEND" -> "Transfert vers " + counterpartyName;
            case "RECEIVE" -> "Réception de " + counterpartyName;
            case "WITHDRAW" -> "Retrait agent Moov";
            case "DEPOSIT" -> "Dépôt agent Moov";
            case "PAYMENT" -> "Paiement marchand " + counterpartyName;
            case "AIRTIME" -> "Achat crédit téléphonique Moov";
            default -> "Transaction Moov Money";
        };
    }

    private void simulateLatency() {
        try {
            Thread.sleep(150 + RANDOM.nextInt(350));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
