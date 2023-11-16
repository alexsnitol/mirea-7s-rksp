package com.example.task5.service;

import com.example.task5.client.fileservice.dto.FileResponseDto;
import com.example.task5.client.fileservice.dto.FileWithDataResponseDto;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GameCoverService {

    Mono<FileWithDataResponseDto> getCoverFileByGameId(UUID gameId);

    Mono<FileResponseDto> putCoverFileByGameId(UUID gameId, FilePart file);

    Mono<Void> deleteCoverFileByGameId(UUID gameId);

}
