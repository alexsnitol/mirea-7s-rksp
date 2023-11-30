package com.example.gameservice.service.impl;

import com.example.gameservice.client.fileservice.FileClient;
import com.example.gameservice.client.fileservice.dto.FileResponseDto;
import com.example.gameservice.client.fileservice.dto.FileWithDataResponseDto;
import com.example.gameservice.repository.GameRepository;
import com.example.gameservice.service.GameCoverService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class GameCoverServiceImpl implements GameCoverService {

    private final FileClient fileClient;
    private final GameRepository gameRepository;


    public GameCoverServiceImpl(FileClient fileClient, GameRepository gameRepository) {
        this.fileClient = fileClient;
        this.gameRepository = gameRepository;
    }


    @Override
    public Mono<FileWithDataResponseDto> getCoverFileByGameId(UUID gameId) {
        return gameRepository.findById(gameId)
                .flatMap(game -> fileClient.getById(game.getCoverFileId()));
    }

    @Override
    public Mono<FileResponseDto> putCoverFileByGameId(UUID gameId, FilePart filePart) {
        return fileClient.upload(filePart)
                .flatMap(coverFile -> setCoverToGameById(gameId, coverFile));
    }

    private Mono<FileResponseDto> setCoverToGameById(UUID gameId, FileResponseDto coverFile) {
        return gameRepository.findById(gameId).flatMap(game -> {
            UUID currentGameCoverFileId = game.getCoverFileId();
            if (nonNull(currentGameCoverFileId)) {
                return fileClient.deleteById(currentGameCoverFileId)
                        .then(Mono.defer(() -> {
                            game.setCoverFileId(coverFile.id());
                            return gameRepository.save(game);
                        }))
                        .then(Mono.just(coverFile));
            } else {
                game.setCoverFileId(coverFile.id());
                return gameRepository.save(game)
                        .then(Mono.just(coverFile));
            }
        });
    }

    @Override
    public Mono<Void> deleteCoverFileByGameId(UUID gameId) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    UUID coverFileId = game.getCoverFileId();
                    game.setCoverFileId(null);
                    return gameRepository.save(game)
                            .then(Mono.defer(() -> fileClient.deleteById(coverFileId)));
                });
    }

}
