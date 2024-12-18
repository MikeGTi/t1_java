package ru.mboychook.kafka.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.mboychook.model.Transaction;
import ru.mboychook.model.dto.TransactionDto;
import ru.mboychook.service.impl.TransactionFirstServiceImpl;
import ru.mboychook.util.TransactionMapper;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionAcceptedResultConsumer<T extends TransactionDto> {

    private final TransactionFirstServiceImpl transactionFirstService;
    private final TransactionMapper transactionMapper;

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
                   topics = "${t1.kafka.topic.transaction-registration}, ${t1.kafka.topic.transaction-result}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<T> messageList,
                                  Acknowledgment ack) {

        log.debug("Transaction consumer: Обработка новых сообщений");
        try {
            List<Transaction> transactions = messageList.stream()
                                                        .map(transactionMapper::toEntity)
                                                        .toList();
            transactionFirstService.handle(transactions);
        } finally {
            ack.acknowledge();
        }
        log.debug("Transaction consumer: записи обработаны");
    }
}
