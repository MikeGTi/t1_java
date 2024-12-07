package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.kafka.producers.KafkaMetricsProducer;
import ru.t1.java.demo.model.dto.MetricStatisticDto;
import java.util.Arrays;

@Async
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {

    private final KafkaMetricsProducer kafkaMetricsProducer;

    @Value("${track.method-execution-time-limit-ms}")
    private Long timeLimitMs;

    @Pointcut("within(ru.t1.java.demo.*)")
    public void checkMethodsExecutionTime() {
    }

    @Around("@annotation(ru.t1.java.demo.aop.Metric)")
    public Object trackingExecutionTime(ProceedingJoinPoint pJoinPoint) throws Throwable {
        log.info("Вызов метода: {}", pJoinPoint.getSignature().toShortString());

        long beforeTime = System.currentTimeMillis();
        Object result;

        try {
            result = pJoinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - beforeTime;
            log.info("Время исполнения: {} ms", executionTime);

            if(executionTime > timeLimitMs) {
                MetricStatisticDto metricStatisticDto = MetricStatisticDto.builder()
                                                                          .methodName(pJoinPoint.getSignature().getName())
                                                                          .methodArgs(Arrays.toString(pJoinPoint.getArgs()))
                                                                          .executionTime(executionTime)
                                                                          .exceededOnTime(executionTime - timeLimitMs)
                                                                          .build();
                kafkaMetricsProducer.send(metricStatisticDto);
            }
        }
        return result;
    }
}
