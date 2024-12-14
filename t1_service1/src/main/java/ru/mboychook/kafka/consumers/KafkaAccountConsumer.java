package ru.mboychook.kafka.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.mboychook.model.Account;
import ru.mboychook.model.dto.AccountDto;
import ru.mboychook.service.impl.AccountServiceImpl;
import ru.mboychook.util.AccountMapper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaAccountConsumer {

    private final AccountServiceImpl accountServiceImpl;
    private final AccountMapper accountMapper;


    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
                   topics = "${t1.kafka.topic.account-registration}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<AccountDto> messageList,
                         Acknowledgment ack) {
        log.debug("Account consumer: Обработка новых сообщений");

        try {
            List<Account> accounts = messageList.stream()
                                                .map(accountMapper::toEntity)
                                                .toList();
            accountServiceImpl.handle(accounts);
        } finally {
            ack.acknowledge();
        }

        log.debug("Account consumer: записи обработаны");
    }
}
