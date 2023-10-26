package com.example.task5.service;

import com.example.task5.model.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GameService {

    Mono<Game> getById(UUID id);

    Flux<Game> getAll();

    Mono<Game> add(Game game);

    Mono<Void> deleteById(UUID id);

}
