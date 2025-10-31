package com.app.musiclover.service;

import com.app.musiclover.data.model.Mood;
import com.app.musiclover.data.repository.MoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoodServiceTest {

    @Mock
    private MoodRepository moodRepository;

    @InjectMocks
    private MoodServiceImpl moodService;

    @Test
    void testCreateMood() {
        // Arrange
        Mood mood = getMoodMock();
        when(moodRepository.save(any(Mood.class))).thenReturn(mood);

        // Act
        moodService.createMood(mood);

        // Assert
        verify(moodRepository).save(mood);
    }

    @Test
    void testGetMoodById() {
        // Arrange
        Long moodId = 1L;
        Mood mood = getMoodMock();

        when(moodRepository.findById(moodId)).thenReturn(Optional.of(mood));

        // Act
        Mood result = moodService.getMoodById(moodId);

        // Assert
        assertNotNull(result);
        assertEquals(mood.getId(), result.getId());
        assertEquals(mood.getName(), result.getName());

        verify(moodRepository).findById(moodId);
    }

    @Test
    void testGetAllMood() {
        // Arrange
        List<Mood> moods = getMoodsMock();
        when(moodRepository.findAll()).thenReturn(moods);

        // Act
        List<Mood> results = moodService.getAllMoods();

        // Assert
        assertNotNull(results);
        assertEquals(moods.get(0).getId(), results.get(0).getId());
        assertEquals(moods.get(1).getId(), results.get(1).getId());
        assertEquals(moods.get(2).getId(), results.get(2).getId());

        verify(moodRepository).findAll();
    }

    @Test
    void testUpdateMood() {
        // Arrange
        Long originalMoodId = 1L;
        Mood originalMood = getMoodMock();
        Mood moodRequest = new Mood(2L, "Calm", "lmef");

        when(moodRepository.findById(originalMoodId)).thenReturn(Optional.of(originalMood));
        when(moodRepository.save(any(Mood.class))).thenReturn(originalMood);

        // Act
        Mood result = moodService.updateMoodById(originalMoodId, moodRequest);

        // Assert
        assertNotNull(result);
        assertEquals(originalMoodId, result.getId());
        assertEquals(moodRequest.getName(), result.getName());

        verify(moodRepository).findById(originalMoodId);
        verify(moodRepository).save(originalMood);
    }

    private Mood getMoodMock() {
        return new Mood(1L, "Sad", "lmef");
    }

    private List<Mood> getMoodsMock() {
        return Arrays.asList(
                new Mood(1L, "Calm", "lmef"),
                new Mood(2L, "Sad", "lmef"),
                new Mood(3L, "Hopeful", "lme")
        );
    }
}
