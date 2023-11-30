package com.example.task5;

import com.example.gameservice.client.payservice.PayClient;
import com.example.gameservice.client.payservice.PayKafkaClient;
import com.example.gameservice.model.Game;
import com.example.gameservice.repository.GameRepository;
import com.example.gameservice.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplUnitTests {

    @InjectMocks
    GameServiceImpl gameService;

//    @Mock
//    FileRepository fileRepository;
    @Mock
    GameRepository gameRepository;
    @Mock
    PayClient payClient;
    @Mock
    PayKafkaClient payKafkaClient;


    @Test
    void givenGameId_whenGetGameById_thenReturnThisGame() {
        // given
        UUID gameId = UUID.randomUUID();

        // expected
        Game expectedGame = mock(Game.class);

        // test
        when(gameRepository.findById(gameId)).thenReturn(Mono.just(expectedGame));

        Mono<Game> resultMonoGame = gameRepository.findById(gameId);

        StepVerifier.create(resultMonoGame)
                .expectNext(expectedGame)
                .expectComplete()
                .verify();
    }

    @Test
    void givenNothing_whenGetAllGames_thenReturnAllGames() {
        // expected
        Game expectedGame1 = mock(Game.class);
        Game expectedGame2 = mock(Game.class);

        // test
        when(gameRepository.findAll()).thenReturn(Flux.just(expectedGame1, expectedGame2));

        Flux<Game> resultGameFlux = gameService.getAll();

        StepVerifier.create(resultGameFlux)
                .expectNext(expectedGame1, expectedGame2)
                .expectComplete()
                .verify();
    }

    @Test
    void givenGame_whenAddGame_thenGameIsAdded() {
        // given
        Game expectedGame = mock(Game.class);

        // expected
        Mono<Game> expectedGameMono = Mono.just(expectedGame);

        // test
        when(gameRepository.save(expectedGame)).thenReturn(expectedGameMono);

        Mono<Game> resultGameMono = gameService.add(expectedGame);

        verify(gameRepository, times(1)).save(expectedGame);
//        StepVerifier.create(resultGameMono)
//                .expectNext(expectedGame)
//                .expectComplete()
//                .verify();
    }

    @Test
    void givenGameId_whenDeleteGameById_thenGameIsDeleted() {
        // given
        UUID gameId = UUID.randomUUID();

        // expected
        Game expectedGame = mock(Game.class);
        UUID expectedCoverFileId = UUID.randomUUID();

        // test
        when(gameRepository.findById(gameId)).thenReturn(Mono.just(expectedGame));
        when(expectedGame.getCoverFileId()).thenReturn(expectedCoverFileId);
        when(gameRepository.deleteById(gameId)).thenReturn(Mono.empty());

        gameService.deleteById(gameId).subscribe();

//        verify(fileRepository, times(1)).deleteById(expectedCoverFileId);
        verify(gameRepository, times(1)).deleteById(gameId);
    }

}
