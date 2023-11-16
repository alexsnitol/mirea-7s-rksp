package com.example.payservice.service;

import com.example.payservice.model.Game;
import com.example.payservice.model.Payment;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

public interface PaymentService {

    Mono<Payment> payGameById(UUID gameId, String privateKeyCustomerAccount);

    Mono<BigInteger> getBalance();

    Mono<Void> saveOrUpdateGame(Game game);

}
