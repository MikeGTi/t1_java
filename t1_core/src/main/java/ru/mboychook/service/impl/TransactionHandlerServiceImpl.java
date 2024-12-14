package ru.mboychook.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.kafka.producers.KafkaTransactionProducer;
import ru.mboychook.model.Account;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.dto.TransactionDto;
import ru.mboychook.repository.TransactionRepository;
import ru.mboychook.service.TransactionHandlerService;
import ru.mboychook.util.TransactionMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionHandlerServiceImpl implements TransactionHandlerService {
    @Value("${t1.transaction.perform-period-min-ms}")
    private String transactionPerformPeriod;

    @Value("${t1.kafka.topic.transaction-accept}")
    private String transactionAcceptedTopic;

    @Value("${t1.kafka.topic.transaction-result}")
    private String transactionResultTopic;

    private final AccountServiceImpl accountService;
    private final TransactionRepository transactionRepository;
    private final KafkaTransactionProducer<TransactionDto> producer;
    private final TransactionMapper transactionMapper;

    private Map<UUID, Account> cache = new HashMap<>();

    @Transactional
    @Override
    public void handle(List<Transaction> transactions) {
        // set transaction status

            //get latest transaction

        /*transactions.stream()
                    .filter(transaction ->  {

                        accountService.getAccountById(transaction.getAccountId()).getStatus().equals(AccountStatus.OPEN);

                    } )
                    .forEach(transaction -> transaction.setStatus(TransactionStatus.REQUESTED));*/

        transactionRepository.saveAllAndFlush(transactions);

        // collect Accounts
        List<UUID> accountsUuids = transactions.stream().map(Transaction::getAccountUuid).toList();
        List<Account> accounts = accountsUuids.stream().map(accountService::findByUuid).toList();

        accounts.forEach(account -> cache.putIfAbsent(account.getAccountUuid(), account));

        // handle Accounts balance
        transactions.forEach(transaction -> {
            // set new balance
            Account account = cache.get(transaction.getAccountUuid());
            account.setBalance(account.getBalance().add(transaction.getAmount()));
            // messaging
            producer.sendTo(transactionAcceptedTopic, transactionMapper.toDto(transaction));
        });

    }


}
