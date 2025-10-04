package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.RecommendationHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationHistoryResponse {

    private Long id;
    private String criteria;
    private Set<MusicalPieceResponse> musicalPieceHashSet;

    public RecommendationHistoryResponse(RecommendationHistory recommendationHistory) {
        setId(recommendationHistory.getId());
        setCriteria(recommendationHistory.getCriteria());
        setMusicalPieceHashSet(recommendationHistory.getMusicalPieceHashSet()
                .stream()
                .map(MusicalPieceResponse::new)
                .collect(Collectors.toSet()));
    }
}
