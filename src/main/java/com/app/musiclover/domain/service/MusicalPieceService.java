package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.MusicalPiece;

import java.util.List;

public interface MusicalPieceService {
    MusicalPiece createMusicalPiece(MusicalPiece musicalRequest);

    MusicalPiece getMusicalPieceById(String musicalPieceId);

    List<MusicalPiece> getMusicalPiecesByTitleAndComposerAndEra(String title, String composer, String era);

    MusicalPiece updateMusicalPiece(String musicalPieceId, MusicalPiece musicalPieceRequest);

    void deleteMusicalPieceById(String musicalPieceId);
}
