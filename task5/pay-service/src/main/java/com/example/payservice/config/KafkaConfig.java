package com.example.payservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    private final String payRequestTopic;
    private final String payResponseTopic;


    public KafkaConfig(
            @Value("${spring.kafka.topic.pay-request}")
            String payRequestTopic,
            @Value("${spring.kafka.topic.pay-response}")
            String payResponseTopic
    ) {
        this.payRequestTopic = payRequestTopic;
        this.payResponseTopic = payResponseTopic;
    }


    @Bean
    public NewTopic payRequestTopic() {
        return TopicBuilder.name(payRequestTopic).build();
    }

    @Bean
    public NewTopic payResponseTopic() {
        return TopicBuilder.name(payResponseTopic).build();
    }

}
