package com.example.task5.service;

import com.example.task5.model.File;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GameCoverService {

    Mono<File> getCoverFileByGameId(UUID gameId);

    Mono<File> putCoverFileByGameId(UUID gameId, FilePart file);

    Mono<Void> deleteCoverFileByGameId(UUID gameId);

}
