package ru.mboychook.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mboychook.model.enums.AccountStatus;
import ru.mboychook.model.enums.AccountType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;


/**
 * DTO for {@link ru.mboychook.model.Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto implements Serializable {

    @JsonProperty("account_uuid")
    UUID accountUuid;

    @JsonProperty("client_uuid")
    UUID clientUuid;

    @JsonProperty("account_type")
    AccountType accountType;

    @JsonProperty("status")
    AccountStatus status;

    @JsonProperty("balance")
    BigDecimal balance;

    @JsonProperty("frozen_amount")
    BigDecimal frozenAmount;

    @JsonProperty("transactions")
    Set<TransactionDto> transactions;
}