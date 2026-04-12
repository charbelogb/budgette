package com.budgette.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String id;
    private String userId;
    private Operator operator;
    private Country country;
    private String phoneNumber;
    private String accountId;
    private BigDecimal balance;
    private boolean active;
}
