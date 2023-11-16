package com.example.task5;

import com.example.task5.client.fileservice.dto.FileResponseDto;
import com.example.task5.model.Game;
import com.example.task5.repository.GameRepository;
import com.example.task5.service.impl.GameCoverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameCoverServiceImplUnitTests {

    @InjectMocks
    GameCoverServiceImpl gameCoverService;

    @Mock
    GameRepository gameRepository;


    @Test
    void givenGameId_whenGetGameCoverById_thenGetItCoverFile() {
        // given
        UUID gameId = UUID.randomUUID();

        // expected
        FileResponseDto expectedFile = mock(FileResponseDto.class);

        // test
//        when(fileRepository.findByGameCoverFileId(gameId)).thenReturn(Mono.just(expectedFile));

//        File resultFile = gameCoverService.getCoverFileByGameId(gameId).block();

//        assertEquals(expectedFile, resultFile);
    }

    @Test
    void givenFileAndGameId_whenPutCoverFileByGameId_thenSaveFileAndSetCoverFileToGame() {
        // given
        UUID gameId = UUID.randomUUID();
        FilePart filePart = mock(FilePart.class);
        DataBuffer dataBuffer = mock(DataBuffer.class);

        // expected
        FileResponseDto expectedFile = mock(FileResponseDto.class);
        Game expectedGame = mock(Game.class);

        // test
        when(filePart.content()).thenReturn(Flux.just(dataBuffer));
        when(dataBuffer.readableByteCount()).thenReturn(0);
//        when(fileRepository.save(any(File.class))).thenReturn(Mono.just(expectedFile));
        when(gameRepository.findById(gameId)).thenReturn(Mono.just(expectedGame));
        when(expectedGame.getCoverFileId()).thenReturn(null);
        when(gameRepository.save(expectedGame)).thenReturn(Mono.just(expectedGame));

        gameCoverService.putCoverFileByGameId(gameId, filePart).block();

//        verify(fileRepository, times(1)).save(any(File.class));
        verify(gameRepository, times(1)).save(expectedGame);
    }

    @Test
    void givenGameId_whenDeleteCoverFileByGameId_thenFileIsDeletedAndGameCoverSettingToNull() {
        // given
        UUID gameId = UUID.randomUUID();

        // expected
        Game expectedGame = mock(Game.class);
        UUID expectedFileId = UUID.randomUUID();

        // test
        when(gameRepository.findById(gameId)).thenReturn(Mono.just(expectedGame));
        when(expectedGame.getCoverFileId()).thenReturn(expectedFileId);
        when(gameRepository.save(expectedGame)).thenReturn(Mono.just(expectedGame));
//        when(fileRepository.deleteById(expectedFileId)).thenReturn(Mono.empty());

        gameCoverService.deleteCoverFileByGameId(gameId).block();

//        verify(fileRepository, times(1)).deleteById(expectedFileId);
        verify(expectedGame, times(1)).setCoverFileId(null);
        verify(gameRepository, times(1)).save(expectedGame);
    }

}
