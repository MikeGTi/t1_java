package ru.mboychook.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.aop.HandlingResult;
import ru.mboychook.aop.LogException;
import ru.mboychook.aop.Track;
import ru.mboychook.exception.TransactionException;
import ru.mboychook.model.Account;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.dto.TransactionDto;
import ru.mboychook.repository.TransactionRepository;
import ru.mboychook.service.*;
import ru.mboychook.util.TransactionMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService, ParserService<Transaction>, HandleService<Transaction> {

    private final TransactionRepository transactionRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final TransactionMapper transactionMapper;

    /*@PostConstruct
    void init() {
        List<Transaction> transactions = null;
        try {
            transactions = parseJson();
        } catch (IOException e) {
            log.error("Ошибка во время обработки записей", e);
        }
        if (transactions != null) {
            transactionRepository.saveAll(transactions);
        }
    }*/

    @Override
    public List<Transaction> parseJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TransactionDto[] transactions = mapper.readValue(new File("src/main/resources/mock_data/transaction/tbl_transaction.json"),
                TransactionDto[].class);
        return Arrays.stream(transactions)
                .map(transactionMapper::toEntity)
                .toList();
    }

    @Transactional
    @Override
    public void handle(Iterable<Transaction> entities) {
        transactionRepository.saveAllAndFlush(entities);
    }

    @Transactional
    @Override
    public Transaction create(Transaction transaction) throws TransactionException {
        UUID accountUuid = transaction.getAccountUuid();
        Optional<Account> account = Optional.ofNullable(accountServiceImpl.findByUuid(accountUuid));
        if (account.isEmpty()) {
            throw new TransactionException(String.format("Account with uud %s is not exists", accountUuid));
        }
        log.info("Balance of account uuid {} was {}", account.get().getAccountUuid(), account.get().getBalance());
        log.info("New transaction has amount {}", transaction.getAmount());
        account.get().setBalance(account.get().getBalance().add(transaction.getAmount()));
        log.info("New balance of account uuid {} are {} ", account.get().getAccountUuid(), account.get().getBalance());
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Transaction findByUuid(UUID transactionUuid) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionRepository.findByTransactionUuid(transactionUuid));
        if (transaction.isEmpty()) {
            throw new TransactionException(String.format("Account with uuid %s is not exists", transactionUuid));
        }
        return transaction.get();
    }

    @Override
    @Transactional(readOnly = true)
    @LogException
    @Track
    @HandlingResult
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Transactional
    @Override
    public Transaction update(UUID transactionUuid, TransactionDto transactionDto) throws TransactionException {
        Optional<Transaction> transactionToUpdate = Optional.ofNullable(transactionRepository.findByTransactionUuid(transactionUuid));
        if (transactionToUpdate.isEmpty()) {
            throw new TransactionException(String.format("Transaction with uuid %s is not exists", transactionUuid));
        }
        Account account = accountServiceImpl.findByUuid(transactionToUpdate.get().getAccountUuid());

        transactionToUpdate.get().setAmount(transactionDto.getAmount());
        transactionToUpdate.get().setCreated(transactionDto.getCreated());
        transactionToUpdate.get().setAccount(account);

        return transactionRepository.save(transactionToUpdate.get());
    }

    @Transactional
    @Override
    public void delete(UUID transactionUuid) throws TransactionException {
        transactionRepository.delete(transactionRepository.findByTransactionUuid(transactionUuid));
    }
}

