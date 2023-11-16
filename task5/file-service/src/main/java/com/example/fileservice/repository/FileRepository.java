package com.example.fileservice.repository;

import com.example.fileservice.model.File;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends ReactiveCrudRepository<File, UUID> {

}
