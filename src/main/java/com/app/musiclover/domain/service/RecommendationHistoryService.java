package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.RecommendationHistory;

public interface RecommendationHistoryService {
    RecommendationHistory getByDefaultRecommendationHistories();
    RecommendationHistory createRecommendationHistoryByFilter(String moodName, String era, String instrument, Integer  duration);
}
