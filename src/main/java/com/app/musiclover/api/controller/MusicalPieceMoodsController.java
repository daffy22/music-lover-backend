package com.app.musiclover.api.controller;

import com.app.musiclover.api.dto.MoodResponse;
import com.app.musiclover.domain.service.MusicalPieceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MusicalPiecesController.MUSICAL_PIECES)
public class MusicalPieceMoodsController {

    static final String MUSICAL_PIECE_MOODS = "/api/v1/musical-piece-moods";
    static final String MUSICAL_PIECE_ID = "/{musicalPieceId}";
    static final String MOOD_ID = "/{moodId}";

    private final MusicalPieceService musicalPieceService;

    public MusicalPieceMoodsController(MusicalPieceService musicalPieceService) {
        this.musicalPieceService = musicalPieceService;
    }

    @PostMapping(MUSICAL_PIECE_ID + MOOD_ID)
    public ResponseEntity<Void> addMoodToMusicalPiece(@PathVariable Long musicalPieceId, @PathVariable Long moodId) {
        musicalPieceService.addMood(musicalPieceId, moodId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(MUSICAL_PIECE_ID + MOOD_ID)
    public ResponseEntity<Void> removeMusicalPiece(@PathVariable Long musicalPieceId, @PathVariable Long moodId) {
        musicalPieceService.addMood(musicalPieceId, moodId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(MUSICAL_PIECE_ID)
    public ResponseEntity<Set<MoodResponse>> getAllMoodsByMusicalPieceId(@PathVariable Long musicalPieceId) {
        Set<MoodResponse> response = musicalPieceService.getMoodsByMusicalPieceId(musicalPieceId)
                .stream()
                .map(MoodResponse::new)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }
}
