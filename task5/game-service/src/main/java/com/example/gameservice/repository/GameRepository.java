package com.example.gameservice.repository;

import com.example.gameservice.model.Game;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GameRepository extends ReactiveCrudRepository<Game, UUID> {

    @Query("SELECT g.* FROM user_game INNER JOIN game g ON user_game.game_id = g.id WHERE user_id = :userId")
    Flux<Game> getGamesByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("INSERT INTO user_game (user_id, game_id) VALUES (:userId, :gameId)")
    Mono<Void> saveUserGameByIds(UUID userId, UUID gameId);

}
