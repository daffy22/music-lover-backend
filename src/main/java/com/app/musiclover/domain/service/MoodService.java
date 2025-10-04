package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.Mood;

import java.util.List;

public interface MoodService {
    Mood createMood(Mood mood);

    Mood getMoodById(Long moodId);

    List<Mood> getAllMoods();

    Mood updateMoodById(Long moodId, Mood updateMoodRequest);

    void deleteMoodById(Long moodId);
}
