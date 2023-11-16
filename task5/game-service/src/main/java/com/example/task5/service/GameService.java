package com.example.task5.service;

import com.example.task5.client.payservice.dto.Payment;
import com.example.task5.model.Game;
import com.example.task5.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GameService {

    Mono<Game> getById(UUID id);

    Flux<Game> getAll();

    Mono<Game> add(Game game);

    Mono<Void> deleteById(UUID id);

    Flux<Game> getAllByUserId(UUID userId);

    Mono<Payment> payGameByIdFromUser(UUID gameId, User user);

}
