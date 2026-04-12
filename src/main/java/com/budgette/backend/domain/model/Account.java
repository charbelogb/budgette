package com.budgette.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
