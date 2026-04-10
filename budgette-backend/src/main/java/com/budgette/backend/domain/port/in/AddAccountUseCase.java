package com.budgette.backend.domain.port.in;

import com.budgette.backend.domain.model.Account;
import com.budgette.backend.domain.model.Country;
import com.budgette.backend.domain.model.Operator;

public interface AddAccountUseCase {

    record AddAccountCommand(String userId, Operator operator, Country country, String phoneNumber) {}

    Account addAccount(AddAccountCommand command);
}
