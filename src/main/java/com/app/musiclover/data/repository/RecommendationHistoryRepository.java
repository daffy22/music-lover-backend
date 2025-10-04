package com.app.musiclover.data.repository;

import com.app.musiclover.data.model.RecommendationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationHistoryRepository extends JpaRepository<RecommendationHistory, Long> {
}
