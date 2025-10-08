package com.app.musiclover.service;

import com.app.musiclover.data.model.Mood;
import com.app.musiclover.data.repository.MoodRepository;
import com.app.musiclover.data.repository.MusicalPieceRepository;
import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.AuthUserService;
import com.app.musiclover.domain.service.MusicalPieceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MusicalPieceServiceImpl implements MusicalPieceService {

    private final MusicalPieceRepository musicalPieceRepository;
    private final AuthUserService authUserService;
    private final MoodRepository moodRepository;

    public MusicalPieceServiceImpl(MusicalPieceRepository musicalPieceRepository, AuthUserService authUserService, MoodRepository moodRepository) {
        this.musicalPieceRepository = musicalPieceRepository;
        this.authUserService = authUserService;
        this.moodRepository = moodRepository;
    }

    @Override
    public MusicalPiece createMusicalPiece(MusicalPiece musicalRequest) {
        String username = authUserService.getUsername();
        musicalRequest.setCreatedBy(username);
        return musicalPieceRepository.save(musicalRequest);
    }

    @Override
    public MusicalPiece getMusicalPieceById(Long musicalPieceId) {
        return musicalPieceRepository.findById(musicalPieceId)
                .orElseThrow(() -> new NotFoundException("Musical piece id: " + musicalPieceId));
    }

    @Override
    public Page<MusicalPiece> getAll(Pageable pageable) {
        return musicalPieceRepository.findAll(pageable);
    }

    @Override
    public Page<MusicalPiece> getMusicalPiecesByTitleEraAndComposer(String title, String era, String composer, Pageable pageable) {
        return musicalPieceRepository.findByFilters(title, era, composer, pageable);
    }

    @Override
    public MusicalPiece updateMusicalPiece(Long musicalPieceId, MusicalPiece musicalPieceRequest) {
        MusicalPiece musicalPiece = getMusicalPieceById(musicalPieceId);
        musicalPiece.update(musicalPieceRequest);
        return musicalPieceRepository.save(musicalPiece);
    }

    @Override
    public void deleteMusicalPieceById(Long musicalPieceId) {
        if (!musicalPieceRepository.existsById(musicalPieceId)) {
            throw new NotFoundException("Musical piece id: " + musicalPieceId);
        }
        musicalPieceRepository.deleteById(musicalPieceId);
    }

    @Override
    public void addMood(Long musicalPieceId, Long moodId) {
        MusicalPiece musicalPiece = getMusicalPieceById(musicalPieceId);
        Mood mood = moodRepository.findById(moodId)
                .orElseThrow(() -> new NotFoundException("Mood id: " + moodId));
        musicalPiece.addMood(mood);
        musicalPieceRepository.save(musicalPiece);
    }

    @Override
    public void deleteMood(Long musicalPieceId, Long moodId) {
        MusicalPiece musicalPiece = getMusicalPieceById(musicalPieceId);
        Mood mood = moodRepository.findById(moodId)
                .orElseThrow(() -> new NotFoundException("Mood id: " + moodId));
        musicalPiece.removeMood(mood);
        musicalPieceRepository.save(musicalPiece);
    }

    @Override
    public Set<Mood> getMoodsByMusicalPieceId(Long musicalPieceId) {
        return musicalPieceRepository.findAllByMusicalPieceId(musicalPieceId);
    }
}
