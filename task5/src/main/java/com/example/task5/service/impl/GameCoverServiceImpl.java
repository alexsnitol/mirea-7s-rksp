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
import java.util.concurrent.atomic.AtomicReference;

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
        File newCoverFile = new File();
        List<Byte> byteList = new LinkedList<>();
        file.content().doOnNext(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            byteList.addAll(Bytes.asList(bytes));
        }).doOnComplete(() -> {
            byte[] byteArray = Bytes.toArray(byteList);
            newCoverFile.setData(byteArray);
            Mono<File> fileMono = fileRepository.save(newCoverFile);
            fileMono.subscribe(coverFile -> setCoverToGameById(gameId, coverFile));
        }).subscribe();
        return Mono.empty();
    }

    private void setCoverToGameById(UUID gameId, File coverFile) {
        gameRepository.findById(gameId).subscribe(game -> {
            UUID currentGameCoverFileId = game.getCoverFileId();
            if (nonNull(currentGameCoverFileId)) {
                fileRepository.deleteById(currentGameCoverFileId).subscribe();
            }
            game.setCoverFileId(coverFile.getId());
            gameRepository.save(game).subscribe();
        });
    }

    @Override
    public Mono<Void> deleteCoverFileByGameId(UUID gameId) {
        AtomicReference<UUID> coverFileId = new AtomicReference<>();
        gameRepository.findById(gameId).doOnNext(game -> {
            coverFileId.set(game.getCoverFileId());
            game.setCoverFileId(null);
            gameRepository.save(game)
                    .doOnNext(ignored -> fileRepository.deleteById(coverFileId.get()).subscribe())
                    .subscribe();
        }).subscribe();
        return Mono.empty();
    }

}
