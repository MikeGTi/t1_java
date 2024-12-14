package ru.mboychook.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mboychook.model.enums.AccountStatus;
import ru.mboychook.model.enums.AccountType;

import java.io.Serializable;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto implements Serializable {
    @JsonProperty("account_uuid")
    private UUID accountUuid;

    @JsonProperty("client_uuid")
    private UUID clientUuid;

    @NotNull(message = "DEBIT or CREDIT")
    @JsonProperty("account_type")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccountType accountType;

    @JsonProperty("status")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccountStatus status;

    @JsonProperty("balance")
    @NotNull(message = "The balance must be filled in, > 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String balance;

    @JsonProperty("frozen_amount")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String frozenAmount;
}