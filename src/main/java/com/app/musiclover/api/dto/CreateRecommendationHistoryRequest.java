package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.RecommendationHistory;
import jakarta.validation.Valid;
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
public class CreateRecommendationHistoryRequest {

    @Valid
    private Set<CreateMusicalPieceRequest> createMusicalPieceRequestSet;

    public RecommendationHistory toRecommendationHistory() {
        RecommendationHistory recommendationHistory = new RecommendationHistory();
        Set<MusicalPiece> createMusicalPieceRequests = getCreateMusicalPieceRequestSet()
                .stream()
                .map(CreateMusicalPieceRequest::toMusicalRequest)
                .collect(Collectors.toSet());
        recommendationHistory.setMusicalPieceHashSet(createMusicalPieceRequests);
        return recommendationHistory;
    }
}
