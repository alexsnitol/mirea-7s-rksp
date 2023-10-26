package com.example.task5.controller;

import com.example.task5.model.Game;
import com.example.task5.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequestMapping("/games")
@RestController
public class GameController {

    private final GameService gameService;


    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @Operation(description = "Получение игры по идентификатору")
    @GetMapping("/{id}")
    public Mono<Game> getById(@PathVariable UUID id) {
        return gameService.getById(id);
    }

    @Operation(description = "Получение всех игр по идентификатору")
    @GetMapping
    public Flux<Game> getAll() {
        return gameService.getAll();
    }

    @Operation(description = "Добавление игры")
    @PostMapping
    public Mono<Game> add(@RequestBody Game game) {
        return gameService.add(game);
    }

    @Operation(description = "Удаление игры по идентификатору")
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable UUID id) {
        return gameService.deleteById(id);
    }

}
