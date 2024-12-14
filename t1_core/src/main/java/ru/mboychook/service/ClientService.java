package ru.mboychook.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.exception.ClientException;
import ru.mboychook.model.Account;
import ru.mboychook.model.Client;
import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> findAll();

    @Transactional(readOnly = true)
    Client findByClientUuid(UUID clientUuid) throws ClientException;

    @Transactional(readOnly = true)
    Client findByAccountUuid(UUID accountUuid);

    @Transactional(readOnly = true)
    List<Account> findAccountsByClientUuid(UUID clientUuid) throws ClientException;

    @Transactional
    Client createClient(Client entity);

    Client updateClient(UUID clientUuid, Client clientDto) throws ClientException;

    @Transactional
    Client saveClient(Client entity);

    @Transactional
    void deleteClient(UUID clientUuid) throws ClientException;
}
