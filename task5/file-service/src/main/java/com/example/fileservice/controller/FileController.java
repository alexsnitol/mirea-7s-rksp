package com.example.fileservice.controller;

import com.example.fileservice.model.File;
import com.example.fileservice.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequestMapping("/file-service/api/files")
@RestController
public class FileController {

    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @Operation(description = "Получение файла по идентификатору")
    @GetMapping("/{id}")
    public Mono<File> getById(@PathVariable("id") UUID id) {
        return fileService.getById(id);
    }

    @Operation(description = "Загрузка файла")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<File> upload(@RequestPart FilePart file) {
        return fileService.upload(file);
    }

    @Operation(description = "Удаление файла по идентификатору")
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") UUID id) {
        return fileService.deleteById(id);
    }

}
