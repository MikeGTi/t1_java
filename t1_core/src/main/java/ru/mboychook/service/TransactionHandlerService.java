package ru.mboychook.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.model.Transaction;
import java.util.List;

public interface TransactionHandlerService {

    @Transactional
    void handle(List<Transaction> transactions);
}
