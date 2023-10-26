package com.example.task5.controller;

import com.example.task5.model.File;
import com.example.task5.service.GameCoverService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequestMapping("/games/{game_id}/cover")
@RestController
public class GameCoverController {

    private final GameCoverService gameCoverService;


    public GameCoverController(GameCoverService gameCoverService) {
        this.gameCoverService = gameCoverService;
    }


    @Operation(description = "Получение обложки игры по её идентификатору")
    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Mono<byte[]> getCoverByGameId(@PathVariable("game_id") UUID gameId) {
        return gameCoverService.getCoverFileByGameId(gameId).map(File::getData);
    }

    @Operation(description = "Обновление обложки у игры по её идентификатору")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<File> putCoverByGameId(@PathVariable("game_id") UUID gameId, @RequestPart FilePart file) {
        return gameCoverService.putCoverFileByGameId(gameId, file);
    }

    @Operation(description = "Удаление обложки у игры по её идентификатору")
    @DeleteMapping
    public Mono<Void> deleteCoverByGameId(@PathVariable("game_id") UUID gameId) {
        return gameCoverService.deleteCoverFileByGameId(gameId);
    }

}
