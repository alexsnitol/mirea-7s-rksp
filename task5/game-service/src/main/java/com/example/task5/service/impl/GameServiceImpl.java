package com.example.task5.service.impl;

import com.example.task5.client.fileservice.FileClient;
import com.example.task5.client.payservice.PayClient;
import com.example.task5.client.payservice.dto.Payment;
import com.example.task5.model.Game;
import com.example.task5.model.User;
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
    private final FileClient fileClient;
    private final PayClient payClient;


    public GameServiceImpl(
            GameRepository gameRepository,
            FileClient fileClient,
            PayClient payClient
    ) {
        this.gameRepository = gameRepository;
        this.fileClient = fileClient;
        this.payClient = payClient;
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
        return gameRepository.save(game)
                .flatMap(g -> payClient.saveOrUpdateGame(g)
                        .then(Mono.just(g))
                );
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return gameRepository.findById(id)
                .flatMap(game -> gameRepository.deleteById(id)
                        .then(Mono.defer(() -> fileClient.deleteById(game.getCoverFileId()))));
    }

    @Override
    public Flux<Game> getAllByUserId(UUID userId) {
        return gameRepository.getGamesByUserId(userId);
    }

    @Override
    public Mono<Payment> payGameByIdFromUser(UUID gameId, User user) {
        return gameRepository.findById(gameId)
                .flatMap(game -> payClient.payGameById(gameId, user.getCryptoWalletPrivateKey()))
                .flatMap(payment -> {
                    if (payment.getStatus().equals("0x1")) {
                        return gameRepository.saveUserGameByIds(user.getId(), gameId)
                                .then(Mono.just(payment));
                    } else {
                        return Mono.error(new RuntimeException(
                                String.format("Game paying failed, payment code: %s", payment.getStatus()))
                        );
                    }
                });
    }

}
