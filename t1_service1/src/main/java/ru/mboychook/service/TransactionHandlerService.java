package ru.mboychook.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.aop.LogDataSourceError;
import ru.mboychook.model.Transaction;
import java.util.List;

public interface TransactionHandlerService {

    @Transactional
    @LogDataSourceError
    void handle(List<Transaction> transactions);
}
