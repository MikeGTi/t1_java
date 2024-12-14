package ru.mboychook.service.impl;

import ru.mboychook.model.dto.AccountDto;
import ru.mboychook.model.dto.ClientDto;
import ru.mboychook.model.dto.UnblockDto;
import ru.mboychook.service.UnlockService;
import org.springframework.stereotype.Service;

@Service
public class UnlockServiceImpl implements UnlockService {
    @Override
    public UnblockDto unlockClient(ClientDto clientDto) {
        return UnblockDto.builder()
                .uuid(clientDto.getClientUuid())
                .isUnblocked(Math.random() < 0.5)
                .build();
    }

    @Override
    public UnblockDto unlockAccount(AccountDto accountDto) {
        return UnblockDto.builder()
                .uuid(accountDto.getAccountUuid())
                .isUnblocked(Math.random() < 0.5)
                .build();
    }
}
