package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recommendation_histories")
public class RecommendationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "recommendation_history_musical_pieces",
            joinColumns = @JoinColumn(name = "recommendation_history_id"),
            inverseJoinColumns = @JoinColumn(name = "musical_piece_id")
    )
    private List<MusicalPiece> musicalPieceArrayList = new ArrayList<>();

    private String criteria;
}
