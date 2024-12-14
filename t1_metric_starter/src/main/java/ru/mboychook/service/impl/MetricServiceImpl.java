package ru.mboychook.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mboychook.mapper.MetricMapper;
import ru.mboychook.model.entity.Metric;
import ru.mboychook.model.dto.MetricDto;
import ru.mboychook.service.MetricService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ru.mboychook.util.KafkaUtils.getKafkaKey;


@Service
@RequiredArgsConstructor
public class MetricServiceImpl implements MetricService<Metric> {

    private final MetricMapper metricMapper;
    private final KafkaTemplate<String, MetricDto> kafkaTemplate;

    @Value("${t1.kafka.topic.methods-metric}")
    public String topic;

    @Value("${t1.kafka.topic.header.methods-metric-header}")
    private String header;

    @Override
    public void send(Metric metric) {
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("metric_type",
                header.getBytes(StandardCharsets.UTF_8)));
        String key = getKafkaKey();
        ProducerRecord<String, MetricDto> record = new ProducerRecord<>(topic,
                1,
                key,
                metricMapper.toDto(metric),
                headers);
        kafkaTemplate.send(record);
    }
}