package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.Mood;
import com.app.musiclover.data.model.MusicalPiece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface MusicalPieceService {
    MusicalPiece createMusicalPiece(MusicalPiece musicalRequest);

    MusicalPiece getMusicalPieceById(Long musicalPieceId);

    Page<MusicalPiece> getAll(Pageable pageable);

    Page<MusicalPiece> getMusicalPiecesByTitleEraAndComposer(String title, String era, String composer, Pageable pageable);

    MusicalPiece updateMusicalPiece(Long musicalPieceId, MusicalPiece musicalPieceRequest);

    void deleteMusicalPieceById(Long musicalPieceId);

    void addMood(Long musicalPieceId, Long moodId);

    void deleteMood(Long musicalPieceId, Long moodId);

    Set<Mood> getMoodsByMusicalPieceId(Long musicalPieceId);
}
