package com.app.musiclover.service;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.repository.MoodRepository;
import com.app.musiclover.data.repository.MusicalPieceRepository;
import com.app.musiclover.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicalPieceServiceTest {

    @Mock
    private MusicalPieceRepository musicalPieceRepository;

    @Mock
    private AuthUserServiceImpl authUserService;

    @Mock
    private MoodRepository moodRepository;

    @InjectMocks
    private MusicalPieceServiceImpl musicalPieceService;

    @Test
    void testCreateMusicalPiece() {
        // Arrange
        MusicalPiece musicalPiece = getMusicalPieceMock();

        when(authUserService.getUsername()).thenReturn("lmef");
        when(musicalPieceRepository.save(any(MusicalPiece.class))).thenReturn(musicalPiece);

        // Act
        MusicalPiece result = musicalPieceService.createMusicalPiece(musicalPiece);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Chopin", result.getComposer());

        verify(authUserService).getUsername();
        verify(musicalPieceRepository).save(any(MusicalPiece.class));
    }

    @Test
    void testGetMusicalPieceById() {
        // Arrange
        when(musicalPieceRepository.findById(1L)).thenReturn(Optional.of(getMusicalPieceMock()));

        // Act
        MusicalPiece result = musicalPieceService.getMusicalPieceById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Chopin", result.getComposer());

        verify(musicalPieceRepository).findById(1L);
    }

    @Test
    void testGetMusicalPieceById_MusicalPiece_NotFound_ThrowsException() {
        // Arrange
        when(musicalPieceRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                musicalPieceService.getMusicalPieceById(1L));

        verify(musicalPieceRepository).findById(1L);
    }

    @Test
    void testGetAll_ReturnsPageOfMusicalPieces() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());

        List<MusicalPiece> musicalPieces = Arrays.asList(
                MusicalPiece.builder()
                        .id(1L)
                        .title("Ballade No. 1 in G minor, Op. 23")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(600)
                        .votes(100)
                        .build(),
                MusicalPiece.builder()
                        .id(2L)
                        .title("Clair de Lune")
                        .composer("Debussy")
                        .era("Impressionist")
                        .instrument("Piano")
                        .duration(300)
                        .votes(220)
                        .build(),
                MusicalPiece.builder()
                        .id(3L)
                        .title("Nocturne in E-flat major, Op. 9 No. 2")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(270)
                        .votes(310)
                        .build()
        );

        Page<MusicalPiece> expectedPage = new PageImpl<>(musicalPieces, pageable, musicalPieces.size());

        when(musicalPieceRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<MusicalPiece> result = musicalPieceService.getAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(3, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getNumber());
        assertEquals(10, result.getSize());
        assertFalse(result.isEmpty());

        assertEquals("Ballade No. 1 in G minor, Op. 23", result.getContent().get(0).getTitle());
        assertEquals("Clair de Lune", result.getContent().get(1).getTitle());

        verify(musicalPieceRepository).findAll(pageable);
    }

    private MusicalPiece getMusicalPieceMock() {
        return MusicalPiece.builder()
                .id(1L)
                .title("Ballade No. 1 in G minor, Op. 23")
                .composer("Chopin")
                .era("Romantic")
                .instrument("Piano")
                .duration(600)
                .url("https://www.youtube.com/watch?v=2q0lVTwJbqs")
                .votes(100)
                .createdBy("lmef")
                .build();
    }
}
