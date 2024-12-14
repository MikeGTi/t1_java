package ru.mboychook.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.mboychook.model.dto.MetricDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static ru.mboychook.util.KafkaUtils.getKafkaKey;


@Async
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {

    private final KafkaTemplate<String, MetricDto> kafkaTemplate;

    @Value("${track.method-execution-time-limit-ms}")
    private Long timeLimitMs;

    @Value("${t1.kafka.topic.header.methods-metric-header}")
    private String header;

    @Around("@annotation(ru.mboychook.aop.Metric)")
    public CompletableFuture<Object> trackingExecutionTime(ProceedingJoinPoint pJoinPoint) throws Throwable {

        log.info("Call method: {}", pJoinPoint.getSignature().toShortString());

        long beforeTime = System.currentTimeMillis();
        Object result;

        try {
            result = pJoinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - beforeTime;

            log.info("Execution time: {} ms, and limit is {} ms", executionTime, timeLimitMs);

            if (executionTime > timeLimitMs) {
                List<Header> headers = new ArrayList<>();
                headers.add(new RecordHeader("metric_type", header.getBytes(StandardCharsets.UTF_8)));
                String key = getKafkaKey();

                MetricDto metricDto = MetricDto.builder()
                        .methodName(pJoinPoint.getSignature().getName())
                        .methodArgs(Arrays.toString(pJoinPoint.getArgs()))
                        .executionTime(executionTime)
                        .create(LocalDateTime.now())
                        .build();

                ProducerRecord<String, MetricDto> record = new ProducerRecord<>(header, 0, key, metricDto, headers);
                kafkaTemplate.send(record);
            }
        }

        return CompletableFuture.completedFuture(result);
    }
}
