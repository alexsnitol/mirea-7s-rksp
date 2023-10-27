package com.example.task5.service.impl;

import com.example.task5.model.File;
import com.example.task5.repository.FileRepository;
import com.example.task5.repository.GameRepository;
import com.example.task5.service.GameCoverService;
import com.google.common.primitives.Bytes;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class GameCoverServiceImpl implements GameCoverService {

    private final FileRepository fileRepository;
    private final GameRepository gameRepository;


    public GameCoverServiceImpl(FileRepository fileRepository, GameRepository gameRepository) {
        this.fileRepository = fileRepository;
        this.gameRepository = gameRepository;
    }


    @Override
    public Mono<File> getCoverFileByGameId(UUID gameId) {
        return fileRepository.findByGameCoverFileId(gameId);
    }

    @Override
    public Mono<File> putCoverFileByGameId(UUID gameId, FilePart file) {
        List<Byte> byteList = new LinkedList<>();
        return file.content().doOnNext(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            byteList.addAll(Bytes.asList(bytes));
        }).then(Mono.defer(() -> {
            byte[] byteArray = Bytes.toArray(byteList);
            File newCoverFile = new File();
            newCoverFile.setData(byteArray);
            return fileRepository.save(newCoverFile)
                    .flatMap(coverFile -> setCoverToGameById(gameId, coverFile));
        }));
    }

    private Mono<File> setCoverToGameById(UUID gameId, File coverFile) {
        return gameRepository.findById(gameId).flatMap(game -> {
            UUID currentGameCoverFileId = game.getCoverFileId();
            if (nonNull(currentGameCoverFileId)) {
                return fileRepository.deleteById(currentGameCoverFileId)
                        .then(Mono.defer(() -> {
                            game.setCoverFileId(coverFile.getId());
                            return gameRepository.save(game);
                        }))
                        .then(Mono.just(coverFile));
            } else {
                game.setCoverFileId(coverFile.getId());
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
                            .then(Mono.defer(() -> fileRepository.deleteById(coverFileId)));
                });
    }

}
