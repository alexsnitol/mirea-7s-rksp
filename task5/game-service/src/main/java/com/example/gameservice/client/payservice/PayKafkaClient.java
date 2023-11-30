package com.example.gameservice.client.payservice;

import com.example.payservice.dto.PayRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PayKafkaClient {

    private final String payRequestsTopic;
    private final KafkaTemplate<String, PayRequestDto> kafkaTemplate;


    public PayKafkaClient(
            @Value("${spring.kafka.topic.pay-request}")
            String payRequestTopic,
            KafkaTemplate<String, PayRequestDto> kafkaTemplate
    ) {
        this.payRequestsTopic = payRequestTopic;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void producePayRequest(UUID gameId, UUID userId, String privateKeyConsumerAccount) {
        try {
            kafkaTemplate.send(
                    payRequestsTopic,
                    userId.toString(),
                    new PayRequestDto(gameId, privateKeyConsumerAccount)
            );
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                    "Produce pay request failed, because: %s",
                    e.getMessage()
            ));
        }
    }

}
