package ru.mboychook.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.aop.LogDataSourceError;
import ru.mboychook.exception.ClientException;
import ru.mboychook.model.Account;
import ru.mboychook.model.Client;
import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> findAll();

    @LogDataSourceError
    @Transactional(readOnly = true)
    Client findByClientUuid(UUID clientUuid) throws ClientException;

    @Transactional(readOnly = true)
    @LogDataSourceError
    Client findByAccountUuid(UUID accountUuid);

    @LogDataSourceError
    @Transactional(readOnly = true)
    List<Account> findAccountsByClientUuid(UUID clientUuid) throws ClientException;

    @LogDataSourceError
    @Transactional
    Client createClient(Client entity);

    @LogDataSourceError
    Client updateClient(UUID clientUuid, Client clientDto) throws ClientException;

    @LogDataSourceError
    @Transactional
    Client saveClient(Client entity);

    @LogDataSourceError
    @Transactional
    void deleteClient(UUID clientUuid) throws ClientException;
}
