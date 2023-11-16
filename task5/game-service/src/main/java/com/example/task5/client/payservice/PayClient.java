package com.example.task5.client.payservice;

import com.example.task5.client.payservice.dto.Payment;
import com.example.task5.model.Game;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class PayClient {

    private static final String PAY_SERVICE_GATEWAY_ENDPOINT = "/pay-service/api";
    private final String payServiceUrl;
    private final String payServiceSaveOrUpdateEndpoint;
    private final String payServicePayGameEndpoint;


    public PayClient(
            EurekaClient eurekaClient,
            @Value("${external-services.pay-service.endpoints.save-or-update-game}")
            String payServiceSaveOrUpdateEndpoint,
            @Value("${external-services.pay-service.endpoints.pay-game}")
            String payServicePayGameEndpoint
    ) {
        String gatewayUrl = eurekaClient.getApplication("gateway").getInstances().get(0).getHomePageUrl();
        this.payServiceUrl = gatewayUrl + PAY_SERVICE_GATEWAY_ENDPOINT;
        this.payServiceSaveOrUpdateEndpoint = payServiceSaveOrUpdateEndpoint;
        this.payServicePayGameEndpoint = payServicePayGameEndpoint;
    }


    public Mono<Void> saveOrUpdateGame(Game game) {
        return WebClient.create()
                .put()
                .uri(payServiceUrl + payServiceSaveOrUpdateEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(game)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Payment> payGameById(UUID gameId, String privateKeyConsumerAccount) {
        return WebClient.create()
                .post()
                .uri(
                        payServiceUrl + payServicePayGameEndpoint,
                        Map.of(
                                "privateKeyConsumerAccount", privateKeyConsumerAccount,
                                "gameId", gameId
                        )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Payment.class);
    }


}
