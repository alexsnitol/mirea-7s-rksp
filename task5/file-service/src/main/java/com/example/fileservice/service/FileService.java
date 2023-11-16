package com.example.fileservice.service;

import com.example.fileservice.model.File;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FileService {

    Mono<File> getById(UUID id);

    Mono<File> upload(FilePart filePart);

    Mono<Void> deleteById(UUID id);

}
