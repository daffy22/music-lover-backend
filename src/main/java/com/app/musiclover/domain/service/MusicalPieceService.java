package com.app.musiclover.domain.service;

import com.app.musiclover.api.dto.MusicalPieceResponse;
import com.app.musiclover.data.model.MusicalPiece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MusicalPieceService {
    MusicalPiece createMusicalPiece(MusicalPiece musicalRequest);

    MusicalPiece getMusicalPieceById(Long musicalPieceId);

    Page<MusicalPiece> getAll(Pageable pageable);

    Page<MusicalPiece> getMusicalPiecesByTitleEraAndComposer(String title, String era, String composer, Pageable pageable);

    MusicalPiece updateMusicalPiece(Long musicalPieceId, MusicalPiece musicalPieceRequest);

    void deleteMusicalPieceById(Long musicalPieceId);
}
