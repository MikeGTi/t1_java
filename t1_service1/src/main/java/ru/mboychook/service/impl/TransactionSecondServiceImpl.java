package ru.mboychook.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.kafka.producers.KafkaJsonMessageProducer;
import ru.mboychook.model.Account;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.enums.TransactionStatus;
import ru.mboychook.repository.TransactionRepository;
import ru.mboychook.service.HandleService;

import java.math.BigDecimal;
import java.time.Period;
import java.util.*;


/**
 * Task 3 Service 2 (accounts cached):<p>
 * - listen messages from transactions accepted topic;<p>
 * - set transaction status;<p>
 * - send messages to result transactions topic;<p>
 **/

/*
 * TODO Move this service in separate application
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionSecondServiceImpl implements HandleService<Transaction> {

    @Value("${t1.kafka.topic.transaction-result}")
    private String topicToSend;

    @Value("${t1.transaction.perform-period-min-ms}")
    private Period blockPeriod;

    private final TransactionRepository transactionRepository;
    private final KafkaJsonMessageProducer producer;

    @Transactional
    @Override
    public void handle(Iterable<Transaction> entities) {
        // set Transaction status
        entities.forEach(transaction -> {
                            BigDecimal balance = transaction.getAccount().getBalance();
                            List<Transaction> list = transactionRepository.findAllTransactionsByCreatedBetween(transaction.getCreated().minus(blockPeriod),
                                                                                                            transaction.getCreated());
                            if (balance.add(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
                             transaction.setStatus(TransactionStatus.REJECTED);
                            } else if (list.size() > 1) {
                             list.forEach(t -> {
                                                 t.setStatus(TransactionStatus.BLOCKED);
                                                 String message = buildJsonMessage(t, t.getAccount());
                                                 producer.sendTo(topicToSend, message);
                             });
                            } else {
                             Account account = transaction.getAccount();
                             account.setBalance(account.getBalance().add(transaction.getAmount()));
                             transaction.setStatus(TransactionStatus.ACCEPTED);
                            }
                        }
        );

        // send message
        entities.forEach(transaction -> {
            String message = buildJsonMessage(transaction, transaction.getAccount());
            producer.sendTo(topicToSend, message);
        });

        //transactionRepository.saveAllAndFlush(entities);
    }

    private String buildJsonMessage(Transaction transaction, Account account) {
        //{transactionStatus, accountId, transactionId}
        return String.format("{%s, %s, %s}", transaction.getStatus(),
                                             account.getAccountUuid(),
                                             transaction.getTransactionUuid());
    }
}