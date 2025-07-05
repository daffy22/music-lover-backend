package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.MusicalPiece;

import java.util.List;

public interface MusicalPieceService {
    MusicalPiece createMusicalPiece(MusicalPiece musicalRequest);

    MusicalPiece getMusicalPieceById(Long musicalPieceId);

    List<MusicalPiece> getMusicalPiecesByTitleAndComposerAndEra(String title, String composer, String era);

    MusicalPiece updateMusicalPiece(Long musicalPieceId, MusicalPiece musicalPieceRequest);

    void deleteMusicalPieceById(Long musicalPieceId);
}
