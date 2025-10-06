package com.app.musiclover.api.controller;

import com.app.musiclover.api.dto.CreateMoodRequest;
import com.app.musiclover.api.dto.MoodResponse;
import com.app.musiclover.data.model.Mood;
import com.app.musiclover.domain.service.MoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(MoodsController.MOODS)
public class MoodsController {

    static final String MOODS = "/api/v1/moods";
    static final String MOOD_ID = "/{moodId}";

    private final MoodService moodService;

    public MoodsController(MoodService moodService) {
        this.moodService = moodService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MoodResponse> createMood(@Valid @RequestBody CreateMoodRequest createMoodRequest) {
        Mood mood = moodService.createMood(createMoodRequest.toMood());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MoodResponse(mood));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(MOOD_ID)
    public ResponseEntity<MoodResponse> getMoodById(@PathVariable Long moodId) {
        Mood mood = moodService.getMoodById(moodId);
        return ResponseEntity.ok(new MoodResponse(mood));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<MoodResponse>> getAllMoods() {
        List<MoodResponse> moodResponseList = moodService.getAllMoods().stream()
                .map(MoodResponse::new)
                .toList();
        return ResponseEntity.ok(moodResponseList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(MOOD_ID)
    public ResponseEntity<MoodResponse> updateMoodById(@PathVariable Long moodId, @Valid @RequestBody CreateMoodRequest updateMoodRequest) {
        Mood mood = moodService.updateMoodById(moodId, updateMoodRequest.toMood());
        return ResponseEntity.ok(new MoodResponse(mood));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(MOOD_ID)
    public ResponseEntity<Void> deleteMoodById(@PathVariable Long moodId) {
        moodService.deleteMoodById(moodId);
        return ResponseEntity.noContent().build();
    }
}
