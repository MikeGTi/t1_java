package ru.mboychook.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.exception.AccountException;
import ru.mboychook.model.Client;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.dto.AccountDto;
import ru.mboychook.model.Account;
import ru.mboychook.repository.AccountRepository;
import ru.mboychook.repository.ClientRepository;
import ru.mboychook.repository.TransactionRepository;
import ru.mboychook.service.GenericService;
import ru.mboychook.service.ParserService;
import ru.mboychook.service.HandleService;
import ru.mboychook.util.AccountMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements GenericService<Account>, ParserService<Account>, HandleService<Account> {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final AccountMapper accountMapper;

    /*@PostConstruct
    void init() {
        List<Account> accounts = null;
        try {
            accounts = parseJson();
        } catch (IOException e) {
            log.error("Ошибка во время обработки записей", e);
        }
        if (accounts != null) {
           accountRepository.saveAll(accounts);
        }
    }*/

    @Override
//    @LogExecution
//    @Track
//    @HandlingResult
    public List<Account> parseJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AccountDto[] accounts = mapper.readValue(new File("src/main/resources/mock_data/account/account.json"),
                                                 AccountDto[].class);
        return Arrays.stream(accounts)
                .map(accountMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void handle(Iterable<Account> entities) {
        accountRepository.saveAllAndFlush(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public Account findById(Long accountId) throws AccountException {
        Optional<Account> account = Optional.of(accountRepository.findById(accountId));
        if (account.isEmpty()) {
            throw new AccountException(String.format("Account with uuid %s is not exists", accountId));
        }
        return account.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Account findByUuid(UUID accountUuid) throws AccountException {
        Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountUuid(accountUuid));
        if (account.isEmpty()) {
            throw new AccountException(String.format("Account with uuid %s is not exists", accountUuid));
        }
        return account.get();
    }

    @Transactional(readOnly = true)
    public List<Account> findAccountsByClientUuid(UUID clientUuid) {
        Client client = clientRepository.findByClientUuid(clientUuid);
        return accountRepository.findAllByClient(client);
    }

    @Transactional
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    public Account update(UUID uuid, Account entity) {
        Account updatedAccount = accountRepository.save(entity);
        UUID clientUuid = entity.getClient().getClientUuid();
        Optional<Client> client = Optional.ofNullable(clientRepository.findByClientUuid(clientUuid));
        if (client.isEmpty()) {
            throw new AccountException(String.format("Client with uuid %s, for Account with uuid %s is not exists", clientUuid, entity.getAccountUuid()));
        }
        return updatedAccount;
    }

    @Transactional
    @Override
    public Account create(Account entity) {
        Account savedAccount = accountRepository.save(entity);
        UUID clientUuid = entity.getClient().getClientUuid();
        Optional<Client> client = Optional.ofNullable(clientRepository.findByClientUuid(clientUuid));
        if (client.isEmpty()) {
            throw new AccountException(String.format("Client with uuid %s, for Account with uuid %s is not exists", clientUuid, entity.getAccountUuid()));
        }
        return savedAccount;
    }

    @Transactional
    @Override
    public void delete(UUID accountUuid) throws AccountException {
        Optional<Account> account =  Optional.ofNullable(accountRepository.findByAccountUuid(accountUuid));
        account.ifPresent(accountRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAllTransactionsByAccountId(UUID accountUuid) throws AccountException {
        Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountUuid(accountUuid));
        if (account.isEmpty()) {
            throw new AccountException(String.format("Account with uuid %s is not exists", accountUuid));
        }
        return transactionRepository.findAllTransactionsByAccount(account.get());
    }
}
