package com.example.gameservice.service;

import com.example.gameservice.model.Game;
import com.example.gameservice.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GameService {

    Mono<Game> getById(UUID id);

    Flux<Game> getAll();

    Mono<Game> add(Game game);

    Mono<Void> deleteById(UUID id);

    Flux<Game> getAllByUserId(UUID userId);

    Mono<Void> payGameByIdFromUser(UUID gameId, User user);

}
