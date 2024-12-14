package ru.mboychook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.model.Account;
import ru.mboychook.model.Client;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Transaction findById(Long transactionId);

    Transaction findByTransactionUuid(UUID transactionUuid);

    List<Transaction> findAllTransactionsByAccount(Account account);

    List<Transaction> findAllTransactionsByClient(Client client);

    List<Transaction> findAllTransactionsByCreatedBetween(LocalDateTime start, LocalDateTime end);

    @Override
    <S extends Transaction> List<S> saveAllAndFlush(Iterable<S> transactions);

    @Transactional
    @Modifying
    @Query("update Transaction t set t.status = ?2 where t.transactionUuid = ?1")
    void updateStatusByTransactionUuid(UUID transactionUuid, TransactionStatus transactionStatus);
}