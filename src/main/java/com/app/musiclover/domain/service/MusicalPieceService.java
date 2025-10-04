package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.MusicalPiece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MusicalPieceService {
    MusicalPiece createMusicalPiece(MusicalPiece musicalRequest);

    MusicalPiece getMusicalPieceById(Long musicalPieceId);

    Page<MusicalPiece> getAllMusicalPiecesByEra(String era, Pageable pageable);

    MusicalPiece updateMusicalPiece(Long musicalPieceId, MusicalPiece musicalPieceRequest);

    void deleteMusicalPieceById(Long musicalPieceId);
}
