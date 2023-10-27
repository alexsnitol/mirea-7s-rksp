package com.example.task5.service.impl;

import com.example.task5.model.Game;
import com.example.task5.repository.FileRepository;
import com.example.task5.repository.GameRepository;
import com.example.task5.service.GameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final FileRepository fileRepository;


    public GameServiceImpl(GameRepository gameRepository, FileRepository fileRepository) {
        this.gameRepository = gameRepository;
        this.fileRepository = fileRepository;
    }


    @Override
    public Mono<Game> getById(UUID id) {
        return gameRepository.findById(id);
    }

    @Override
    public Flux<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public Mono<Game> add(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return gameRepository.findById(id)
                .flatMap(game -> gameRepository.deleteById(id)
                        .then(Mono.defer(() -> fileRepository.deleteById(game.getCoverFileId()))));
    }

}
