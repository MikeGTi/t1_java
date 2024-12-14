package ru.mboychook.service;

import ru.mboychook.model.dto.AccountDto;
import ru.mboychook.model.dto.ClientDto;
import ru.mboychook.model.dto.UnblockDto;

public interface UnlockService {
    UnblockDto unlockClient(ClientDto clientDto);

    UnblockDto unlockAccount(AccountDto accountDto);
}
