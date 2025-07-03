package com.app.musiclover.api.resource;

import com.app.musiclover.api.dto.CreateMusicalPieceRequest;
import com.app.musiclover.api.dto.MusicalPieceResponse;
import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.domain.service.MusicalPieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(MusicalPiecesResource.MUSICAL_PIECES)
public class MusicalPiecesResource {

    static final String MUSICAL_PIECES = "/api/v1/musical-pieces";
    static final String MUSICAL_PIECE_ID = "/{musicalPieceId}";
    static final String SEARCH = "/search";

    private final MusicalPieceService musicalPieceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MusicalPieceResponse> createMusicalPiece
            (@Valid @RequestBody CreateMusicalPieceRequest createMusicalPieceRequest) {
        MusicalPiece musicalPiece = musicalPieceService.createMusicalPiece(createMusicalPieceRequest.toMusicalRequest());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MusicalPieceResponse(musicalPiece));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(MUSICAL_PIECE_ID)
    public ResponseEntity<MusicalPieceResponse> getMusicalPiecesById(@PathVariable String musicalPieceId) {
        MusicalPiece musicalPiece = musicalPieceService.getMusicalPieceById(musicalPieceId);
        return ResponseEntity.ok(new MusicalPieceResponse(musicalPiece));
    }

    @GetMapping(SEARCH)
    public ResponseEntity<List<MusicalPieceResponse>> searchMusicalPiecesByTitleAndComposerAndEra(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String composer,
            @RequestParam(required = false, defaultValue = "") String era
    ) {
        List<MusicalPieceResponse> musicalPieceResponseList =
                musicalPieceService.getMusicalPiecesByTitleAndComposerAndEra(title, composer, era)
                .stream()
                .map(MusicalPieceResponse::new)
                .toList();
        return ResponseEntity.ok(musicalPieceResponseList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(MUSICAL_PIECE_ID)
    public ResponseEntity<MusicalPieceResponse> updateMusicalPiece
            (@PathVariable String musicalPieceId, @Valid @RequestBody CreateMusicalPieceRequest createMusicalPieceRequest) {
        MusicalPiece musicalPiece = musicalPieceService.updateMusicalPiece(musicalPieceId, createMusicalPieceRequest.toMusicalRequest());
        return ResponseEntity.ok(new MusicalPieceResponse(musicalPiece));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(MUSICAL_PIECE_ID)
    public ResponseEntity<Void> deleteMusicalPieceById(@PathVariable String musicalPieceId) {
        musicalPieceService.deleteMusicalPieceById(musicalPieceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
