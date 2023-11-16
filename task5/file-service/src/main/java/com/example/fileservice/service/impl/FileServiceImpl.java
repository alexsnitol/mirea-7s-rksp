package com.example.fileservice.service.impl;

import com.example.fileservice.model.File;
import com.example.fileservice.repository.FileRepository;
import com.example.fileservice.service.FileService;
import com.google.common.primitives.Bytes;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;


    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public Mono<File> getById(UUID id) {
        return fileRepository.findById(id);
    }

    @Override
    public Mono<File> upload(FilePart filePart) {
        List<Byte> byteList = new LinkedList<>();
        return filePart.content().doOnNext(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            byteList.addAll(Bytes.asList(bytes));
        }).then(Mono.defer(() -> {
            byte[] byteArray = Bytes.toArray(byteList);
            File newCoverFile = new File();
            newCoverFile.setData(byteArray);
            return fileRepository.save(newCoverFile);
        }));
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return fileRepository.deleteById(id);
    }
}
