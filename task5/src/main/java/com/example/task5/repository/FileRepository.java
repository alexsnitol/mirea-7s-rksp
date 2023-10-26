package com.example.task5.repository;

import com.example.task5.model.File;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FileRepository extends ReactiveCrudRepository<File, UUID> {

    @Query("""
            SELECT f.*
            FROM game g
            INNER JOIN file f on g.cover_file_id = f.id
            WHERE g.id = :gameId""")
    Mono<File> findByGameCoverFileId(@Param("gameId") UUID gameId);

    @Modifying
    @Query("""
            DELETE FROM file
            WHERE id = (
                SELECT cover_file_id
                FROM game
                WHERE id = :gameId)""")
    Mono<Void> deleteByGameCoverFileId(@Param("gameId") UUID gameId);

}
