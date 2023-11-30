package com.example.payservice.client;

import com.example.payservice.dto.PayResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameKafkaClient {

    private final String payResponseTopic;
    private final KafkaTemplate<String, PayResponseDto> kafkaTemplate;


    public GameKafkaClient(
            @Value("${spring.kafka.topic.pay-response}")
            String payResponseTopic,
            KafkaTemplate<String, PayResponseDto> kafkaTemplate
    ) {
        this.payResponseTopic = payResponseTopic;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void producePayResponse(UUID userId, PayResponseDto payResponseDto) {
        kafkaTemplate.send(payResponseTopic, userId.toString(), payResponseDto);
    }

}
