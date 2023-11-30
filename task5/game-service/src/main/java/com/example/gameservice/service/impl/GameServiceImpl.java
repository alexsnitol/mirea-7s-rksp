package com.example.gameservice.service.impl;

import com.example.gameservice.client.fileservice.FileClient;
import com.example.gameservice.client.payservice.PayClient;
import com.example.gameservice.client.payservice.PayKafkaClient;
import com.example.gameservice.model.Game;
import com.example.gameservice.model.User;
import com.example.gameservice.repository.GameRepository;
import com.example.gameservice.service.GameService;
import com.example.payservice.dto.PayResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final FileClient fileClient;
    private final PayClient payClient;
    private final PayKafkaClient payKafkaClient;


    public GameServiceImpl(
            GameRepository gameRepository,
            FileClient fileClient,
            PayClient payClient,
            PayKafkaClient payKafkaClient
    ) {
        this.gameRepository = gameRepository;
        this.fileClient = fileClient;
        this.payClient = payClient;
        this.payKafkaClient = payKafkaClient;
    }


    @Override
    @Transactional(readOnly = true)
    public Mono<Game> getById(UUID id) {
        return gameRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<Game> add(Game game) {
        return gameRepository.save(game)
                .flatMap(g -> payClient.saveOrUpdateGame(g)
                        .then(Mono.just(g))
                );
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(UUID id) {
        return gameRepository.findById(id)
                .flatMap(game -> gameRepository.deleteById(id)
                        .then(Mono.defer(() -> fileClient.deleteById(game.getCoverFileId()))));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Game> getAllByUserId(UUID userId) {
        return gameRepository.getGamesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Void> payGameByIdFromUser(UUID gameId, User user) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    payKafkaClient.producePayRequest(gameId, user.getId(), user.getCryptoWalletPrivateKey());
                    return Mono.empty();
                });
    }

    @KafkaListener(topics = "${spring.kafka.topic.pay-response}")
    public void consumePayments(PayResponseDto payment, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String userId) {
        if (payment.status().equals("0x1")) {
            gameRepository.saveUserGameByIds(UUID.fromString(userId), payment.gameId()).block();
        } else {
            throw new RuntimeException(String.format(
                    "Game paying failed, payment code: %s",
                    payment.status()
            ));
        }
    }

}
