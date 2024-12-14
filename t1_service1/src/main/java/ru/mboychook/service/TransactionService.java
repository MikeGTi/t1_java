package ru.mboychook.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.exception.TransactionException;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.dto.TransactionDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    @Transactional
    Transaction create(Transaction transaction) throws TransactionException;

    @Transactional(readOnly = true)
    Transaction findByUuid(UUID uuid);

    @Transactional(readOnly = true)
    List<Transaction> findAll();

    @Transactional
    Transaction update(UUID transactionUuid, TransactionDto transactionDto) throws TransactionException;

    @Transactional
    void delete(UUID transactionUuid) throws TransactionException;
}
