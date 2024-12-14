package ru.mboychook.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaTopicConfig {

    @Value("${t1.kafka.topic.methods-metric}")
    public String metricsTopic;

    @Value("${t1.kafka.topic.data-source-errors}")
    public String dataSourceErrorsTopic;

    @Bean
    public NewTopic metrics() {
        return TopicBuilder.name(metricsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic dataSourceErrorLog() {
        return TopicBuilder.name(dataSourceErrorsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

}