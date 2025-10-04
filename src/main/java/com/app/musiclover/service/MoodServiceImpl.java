package com.app.musiclover.service;

import com.app.musiclover.data.repository.MoodRepository;
import com.app.musiclover.data.model.Mood;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.MoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodServiceImpl implements MoodService {

    private final MoodRepository moodRepository;

    public MoodServiceImpl(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public Mood createMood(Mood mood) {
        return moodRepository.save(mood);
    }

    @Override
    public Mood getMoodById(Long moodId) {
        return moodRepository.findById(moodId)
                .orElseThrow(() -> new NotFoundException("Mood id: " + moodId));
    }

    @Override
    public List<Mood> getAllMoods() {
        return moodRepository.findAll();
    }

    @Override
    public Mood updateMoodById(Long moodId, Mood updateMoodRequest) {
        Mood mood = getMoodById(moodId);
        mood.update(updateMoodRequest);
        return moodRepository.save(mood);
    }

    @Override
    public void deleteMoodById(Long moodId) {
        if (!moodRepository.existsById(moodId)) {
            throw new NotFoundException("Mood id: " + moodId);
        }
        moodRepository.deleteById(moodId);
    }
}
