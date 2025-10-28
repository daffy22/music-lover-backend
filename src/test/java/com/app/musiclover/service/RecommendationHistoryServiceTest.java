package com.app.musiclover.service;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.RecommendationHistory;
import com.app.musiclover.data.model.Role;
import com.app.musiclover.data.model.User;
import com.app.musiclover.data.repository.MusicalPieceRepository;
import com.app.musiclover.data.repository.UserRepository;
import com.app.musiclover.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationHistoryServiceTest {

    @Mock
    private MusicalPieceRepository musicalPieceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthUserServiceImpl authUserService;

    @InjectMocks
    private RecommendationHistoryServiceImpl recommendationHistoryService;

    @Test
    void testGetByDefaultRecommendationHistories_ReturnsNonEmptySet() {
        // Arrange
        List<MusicalPiece> musicalPieces = createTestMusicalPieces();
        when(musicalPieceRepository.findAll()).thenReturn(musicalPieces);

        // Act
        RecommendationHistory result = recommendationHistoryService.getByDefaultRecommendationHistories();

        // Assert
        assertNotNull(result);
        assertFalse(result.getMusicalPieceHashSet().isEmpty());
    }

    @Test
    void testGetByDefaultRecommendationHistories_ReturnsTopVotedPieces() {
        // Arrange
        List<MusicalPiece> musicalPieces = createTestMusicalPieces();
        when(musicalPieceRepository.findAll()).thenReturn(musicalPieces);

        // Act
        RecommendationHistory result = recommendationHistoryService.getByDefaultRecommendationHistories();

        // Assert
        Set<MusicalPiece> pieces = result.getMusicalPieceHashSet();
        assertTrue(pieces.stream().anyMatch(mp -> "Piano Concerto No. 2 in C minor, Op. 18"
                .equals(mp.getTitle())));
    }

    @Test
    void testGetByDefaultRecommendationHistories_EmptyRepository() {
        // Arrange
        when(musicalPieceRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        RecommendationHistory result = recommendationHistoryService.getByDefaultRecommendationHistories();

        // Assert
        assertNotNull(result);
        assertTrue(result.getMusicalPieceHashSet().isEmpty());
    }

    private List<MusicalPiece> createTestMusicalPieces() {
        List<MusicalPiece> musicalPiecesList = Arrays.asList(
                MusicalPiece.builder().id(1L)
                        .title("Ballade No. 1 in G minor, Op. 23")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(600)
                        .url("https://www.youtube.com/watch?v=2q0lVTwJbqs")
                        .votes(100)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(2L)
                        .title("Fantaisie-Impromptu, Op. 66")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(354)
                        .url("https://youtu.be/Gus4dnQuiGk?si=Tq5_0jvxlmg1inu7")
                        .votes(40)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(3L)
                        .title("The Four Seasons: Winter, RV 297")
                        .composer("Vivaldi")
                        .era("Baroque")
                        .instrument("Violin")
                        .duration(566)
                        .url("https://youtu.be/ZPdk5GaIDjo?si=FgbIGz6fLNJEB9ya")
                        .votes(125)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(4L)
                        .title("Symphony No. 5 in C minor, Op. 67")
                        .composer("Beethoven")
                        .era("Romantic")
                        .instrument("Orchestra")
                        .duration(2112)
                        .url("https://youtu.be/fOk8Tm815lE?si=koEx7UumF8eLwIWr")
                        .votes(200)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(5L)
                        .title("Eine kleine Nachtmusik, K. 525")
                        .composer("Mozart")
                        .era("Classical")
                        .instrument("Strings")
                        .duration(1050)
                        .url("https://youtu.be/oy2zDJPIgwc?si=G4qGgAGkMFxikYbY")
                        .votes(175)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(6L)
                        .title("Clair de Lune")
                        .composer("Debussy")
                        .era("Impressionist")
                        .instrument("Piano")
                        .duration(300)
                        .url("https://youtu.be/CvFH_6DNRCY?si=YoqJW4UvY4vF0zCP")
                        .votes(220)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(7L)
                        .title("Nocturne in E-flat major, Op. 9 No. 2")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(270)
                        .url("https://youtu.be/9E6b3swbnWg?si=w6pCq1H7rjKJhQol")
                        .votes(310)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(8L)
                        .title("Also sprach Zarathustra, Op. 30")
                        .composer("Richard Strauss")
                        .era("Late Romantic")
                        .instrument("Orchestra")
                        .duration(1860)
                        .url("https://youtu.be/Szdziw4tI9o?si=ENb9js26yDEzNR1B")
                        .votes(190)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(9L)
                        .title("Boléro")
                        .composer("Maurice Ravel")
                        .era("Modern")
                        .instrument("Orchestra")
                        .duration(900)
                        .url("https://youtu.be/r30D3SW4OVw?si=kA6M-F3mF3QH4sD4")
                        .votes(260)
                        .createdBy("lmef")
                        .build(),

                MusicalPiece.builder().id(10L)
                        .title("Piano Concerto No. 2 in C minor, Op. 18")
                        .composer("Rachmaninoff")
                        .era("Romantic")
                        .instrument("Piano and Orchestra")
                        .duration(2000)
                        .url("https://youtu.be/rEGOihjqO9w?si=pp3ZBflT7sK1HV_1")
                        .votes(275)
                        .createdBy("lmef")
                        .build()
        );
        return musicalPiecesList;
    }

    @Test
    void createRecommendationHistoryByFilter_ReturnsNewRecommendationHistory() {
        // Arrange
        String moodName = "Happy", era = "Romantic", instrument = "Piano";
        int duration = 500;

        User user = User.builder()
                .id("1aec94d7-529c-4645-bf58-6140a3c1cf08")
                .username("lmef")
                .email("lmef@mail.com")
                .password("randomPassword123@")
                .active(true)
                .role(Role.USER)
                .recommendationHistoriesArrayList(new ArrayList<>())
                .build();

        List<MusicalPiece> filteredPieces = Arrays.asList(
                MusicalPiece.builder()
                        .id(1L)
                        .title("Nocturne in E-flat major, Op. 9 No. 2")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(270)
                        .votes(310)
                        .build(),
                MusicalPiece.builder()
                        .id(2L)
                        .title("Ballade No. 1 in G minor, Op. 23")
                        .composer("Chopin")
                        .era("Romantic")
                        .instrument("Piano")
                        .duration(600)
                        .votes(100)
                        .build()
        );

        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.of(user));
        when(musicalPieceRepository.findByFilters(era, instrument, duration)).thenReturn(filteredPieces);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        RecommendationHistory result = recommendationHistoryService.createRecommendationHistoryByFilter(moodName, era, instrument, duration);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getMusicalPieceHashSet());
        assertEquals(2, result.getMusicalPieceHashSet().size());
        assertNotNull(result.getCriteria());
        assertTrue(result.getCriteria().contains("romantic"));
        assertTrue(result.getCriteria().contains("piano"));

        verify(authUserService).getUsername();
        verify(userRepository).findByUsername("lmef");
        verify(musicalPieceRepository).findByFilters(era, instrument, duration);
        verify(userRepository).save(user);

        assertEquals(1, user.getRecommendationHistoriesArrayList().size());
    }

    @Test
    void createRecommendationHistoryByFilter_UserNotFound_ThrowsException() {
        // Arrange
        String moodName = "Happy", era = "Romantic", instrument = "Piano";
        int duration = 500;

        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                recommendationHistoryService.createRecommendationHistoryByFilter(
                        moodName, era, instrument, duration));

        verify(authUserService).getUsername();
        verify(userRepository).findByUsername("lmef");
        verify(musicalPieceRepository, never()).findByFilters(any(), any(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void createRecommendationHistoryByFilter_NoMatchingPieces_ReturnsEmptySet() {
        // Arrange
        String moodName = "Sad", era = "Baroque", instrument = "Cello";
        int duration = 300;

        User user = User.builder()
                .id("1aec94d7-529c-4645-bf58-6140a3c1cf08")
                .username("lmef")
                .email("lmef@mail.com")
                .password("randomPassword123@")
                .active(true)
                .role(Role.USER)
                .recommendationHistoriesArrayList(new ArrayList<>())
                .build();

        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.of(user));
        when(musicalPieceRepository.findByFilters(era, instrument, duration))
                .thenReturn(Collections.emptyList());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        RecommendationHistory result = recommendationHistoryService.createRecommendationHistoryByFilter(
                moodName, era, instrument, duration);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getMusicalPieceHashSet());
        assertTrue(result.getMusicalPieceHashSet().isEmpty());
        assertNotNull(result.getCriteria());

        verify(userRepository).save(user);
    }

    @Test
    void createRecommendationHistoryByFilter_WithNullParameters_HandlesGracefully() {
        // Arrange
        User user = User.builder()
                .id("1aec94d7-529c-4645-bf58-6140a3c1cf08")
                .username("lmef")
                .email("lmef@mail.com")
                .password("randomPassword123@")
                .active(true)
                .role(Role.USER)
                .recommendationHistoriesArrayList(new ArrayList<>())
                .build();

        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.of(user));
        when(musicalPieceRepository.findByFilters(null, null, null))
                .thenReturn(Collections.emptyList());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        RecommendationHistory result = recommendationHistoryService.createRecommendationHistoryByFilter(
                null, null, null, null);

        // Assert
        assertNotNull(result);
        verify(musicalPieceRepository).findByFilters(null, null, null);
    }

    @Test
    void createRecommendationHistoryByFilter_CriteriaIsProperlyBuilt() {
        // Arrange
        String moodName = "Energetic", era = "Classical", instrument = "Violin";
        int duration = 400;

        User user = User.builder()
                .id("1aec94d7-529c-4645-bf58-6140a3c1cf08")
                .username("lmef")
                .email("lmef@mail.com")
                .password("randomPassword123@")
                .active(true)
                .role(Role.USER)
                .recommendationHistoriesArrayList(new ArrayList<>())
                .build();

        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.of(user));
        when(musicalPieceRepository.findByFilters(era, instrument, duration))
                .thenReturn(Collections.emptyList());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        RecommendationHistory result = recommendationHistoryService.createRecommendationHistoryByFilter(
                moodName, era, instrument, duration);

        // Assert
        String criteria = result.getCriteria();
        assertNotNull(criteria);
        assertTrue(criteria.contains("energetic"));
        assertTrue(criteria.contains("classical"));
    }
}
