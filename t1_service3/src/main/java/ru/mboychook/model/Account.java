package ru.mboychook.model;

import lombok.*;
import ru.mboychook.model.enums.AccountStatus;
import ru.mboychook.model.enums.AccountType;

import java.math.BigDecimal;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    private UUID accountUuid;

    private UUID clientUuid;

    private AccountType accountType;

    private AccountStatus status;

    private BigDecimal balance;

    private BigDecimal frozenAmount;
}
