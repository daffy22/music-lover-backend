package com.app.musiclover.api.controller;

import com.app.musiclover.api.dto.RecommendationHistoryResponse;
import com.app.musiclover.data.model.RecommendationHistory;
import com.app.musiclover.domain.service.RecommendationHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RecommendationHistoriesController.RECOMMENDATION_HISTORIES)
public class RecommendationHistoriesController {

    static final String RECOMMENDATION_HISTORIES = "/api/v1/recommendation-histories";
    static final String RECOMMENDATION_HISTORY_ID = "/{recommendationHistoryId}";
    static final String RECOMMENDATION_HISTORY_DEFAULT = "/recommendationHistoryDefault";

    private final RecommendationHistoryService recommendationHistoryService;

    public RecommendationHistoriesController(RecommendationHistoryService recommendationHistoryService) {
        this.recommendationHistoryService = recommendationHistoryService;
    }

    @GetMapping(RECOMMENDATION_HISTORY_DEFAULT)
    public ResponseEntity<RecommendationHistoryResponse> getRecommendationHistoryByDefault() {
        RecommendationHistoryResponse response =
                new RecommendationHistoryResponse(recommendationHistoryService.getByDefaultRecommendationHistories());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<RecommendationHistoryResponse> getRecommendationHistoryByFilters(
            @RequestParam(required = false) String moodName,
            @RequestParam(required = false) String era,
            @RequestParam(required = false) String instrument,
            @RequestParam(required = false) Integer duration) {
        RecommendationHistory recommendationHistory = recommendationHistoryService
                .createRecommendationHistoryByFilter(moodName, era, instrument, duration);
        return ResponseEntity.ok().body(new RecommendationHistoryResponse(recommendationHistory));
    }
}
