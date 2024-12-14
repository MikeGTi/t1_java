package ru.mboychook.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.exception.AccountException;
import ru.mboychook.model.Account;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<Account> parseJson() throws IOException;

    @Transactional
    void register(List<Account> accounts);

    @Transactional(readOnly = true)
    Account getAccountsByAccountUuid(UUID accountUuid);

    @Transactional(readOnly = true)
    List<Account> getAccountsByClientUuid(UUID clientUuid);

    @Transactional(readOnly = true)
    List<Account> getAccountsByAccountUuid(List<UUID> accountUuids);

    @Transactional(readOnly = true)
    List<Account> findAll();

    @Transactional
    Account save(Account entity);

    @Transactional
    void delete(UUID accountUuid) throws AccountException;
}