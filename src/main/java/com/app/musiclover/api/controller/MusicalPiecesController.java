package com.app.musiclover.api.controller;

import com.app.musiclover.api.dto.CreateMusicalPieceRequest;
import com.app.musiclover.api.dto.MusicalPieceResponse;
import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.domain.service.MusicalPieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(MusicalPiecesController.MUSICAL_PIECES)
@RequiredArgsConstructor
public class MusicalPiecesController {

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
    public ResponseEntity<MusicalPieceResponse> getMusicalPiecesById(@PathVariable Long musicalPieceId) {
        MusicalPiece musicalPiece = musicalPieceService.getMusicalPieceById(musicalPieceId);
        return ResponseEntity.ok(new MusicalPieceResponse(musicalPiece));
    }

    @GetMapping
    public ResponseEntity<Page<MusicalPieceResponse>> getAllMusicalPieces(Pageable pageable) {
        Page<MusicalPiece> page = musicalPieceService.getAll(pageable);

        List<MusicalPieceResponse> musicalPieceResponseList = page.getContent()
                .stream()
                .map(MusicalPieceResponse::new)
                .toList();

        Page<MusicalPieceResponse> responsePage = new PageImpl<>(
                musicalPieceResponseList,
                pageable,
                page.getTotalElements()
        );

        return ResponseEntity.ok(responsePage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(SEARCH)
    public ResponseEntity<Page<MusicalPieceResponse>> searchMusicalPiecesByTitleAndComposerAndEra(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String era,
            @RequestParam(required = false) String composer,
            Pageable pageable
    ) {
        Page<MusicalPiece> page = musicalPieceService.getMusicalPiecesByTitleEraAndComposer(title, era, composer, pageable);
        List<MusicalPieceResponse> musicalPieceResponseList = page.getContent()
                .stream()
                .map(MusicalPieceResponse::new)
                .toList();
        Page<MusicalPieceResponse> responsePage = new PageImpl<>(
                musicalPieceResponseList,
                pageable,
                page.getTotalElements()
        );
        return ResponseEntity.ok(responsePage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(MUSICAL_PIECE_ID)
    public ResponseEntity<MusicalPieceResponse> updateMusicalPiece
            (@PathVariable Long musicalPieceId, @Valid @RequestBody CreateMusicalPieceRequest updateMusicalPieceRequest) {
        MusicalPiece musicalPiece = musicalPieceService.updateMusicalPiece(musicalPieceId, updateMusicalPieceRequest.toMusicalRequest());
        return ResponseEntity.ok(new MusicalPieceResponse(musicalPiece));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(MUSICAL_PIECE_ID)
    public ResponseEntity<Void> deleteMusicalPieceById(@PathVariable Long musicalPieceId) {
        musicalPieceService.deleteMusicalPieceById(musicalPieceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
