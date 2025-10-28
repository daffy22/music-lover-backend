package com.app.musiclover.service;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.RecommendationHistory;
import com.app.musiclover.data.model.User;
import com.app.musiclover.data.repository.MusicalPieceRepository;
import com.app.musiclover.data.repository.UserRepository;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.AuthUserService;
import com.app.musiclover.domain.service.RecommendationHistoryService;
import com.app.musiclover.util.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RecommendationHistoryServiceImpl implements RecommendationHistoryService {

    static final String BAROQUE = "Baroque";
    static final String CLASSICAL = "Classical";
    static final String ROMANTIC = "Romantic";
    static final String IMPRESSIONIST = "Impressionist";

    private final MusicalPieceRepository musicalPieceRepository;
    private final UserRepository userRepository;
    private final AuthUserService authUserService;

    @Override
    public RecommendationHistory getByDefaultRecommendationHistories() {
        List<MusicalPiece> allMusicalPieces = musicalPieceRepository.findAll();

        Set<MusicalPiece> bestMusicalPieces = Stream.of(
                getTopFiveByEra(allMusicalPieces, BAROQUE),
                getTopFiveByEra(allMusicalPieces, CLASSICAL),
                getTopFiveByEra(allMusicalPieces, ROMANTIC),
                getTopFiveByEra(allMusicalPieces, IMPRESSIONIST)
        ).flatMap(List::stream).collect(Collectors.toSet());;

        RecommendationHistory recommendationHistory = new RecommendationHistory();
        recommendationHistory.setMusicalPieceHashSet(bestMusicalPieces);
        return recommendationHistory;
    }

    private List<MusicalPiece> getTopFiveByEra(List<MusicalPiece> allMusicalPieces, String era) {
        return allMusicalPieces.stream()
                .filter(mp -> era.equals(mp.getEra()))
                .sorted(Comparator.comparing(MusicalPiece::getVotes).reversed())
                .limit(5)
                .toList();
    }

    @Override
    public RecommendationHistory createRecommendationHistoryByFilter(String moodName, String era, String instrument, Integer duration) {
        User user = userRepository.findByUsername(authUserService.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        RecommendationHistory recommendationHistory = new RecommendationHistory();
        String criteria = CriteriaBuilder.buildJsonCriteria(moodName, era, instrument, duration);
        recommendationHistory.setCriteria(criteria);
        // TODO: ADD MOD NAME PARAMETER TO THE FILTERS IN '.findByFilters() method'
        Set<MusicalPiece> musicalPieceSet = new HashSet<>(musicalPieceRepository.findByFilters(era, instrument, duration));
        recommendationHistory.setMusicalPieceHashSet(musicalPieceSet);
        user.addRecommendationHistory(recommendationHistory);
        userRepository.save(user);
        return recommendationHistory;
    }
}
