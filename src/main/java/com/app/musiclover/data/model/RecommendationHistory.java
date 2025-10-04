package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @Column(length = 100, nullable = false)
    private String criteria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recommendation_history_musical_pieces",
            joinColumns = @JoinColumn(name = "recommendation_history_id"),
            inverseJoinColumns = @JoinColumn(name = "musical_piece_id")
    )
    private Set<MusicalPiece> musicalPieceHashSet = new HashSet<>();
}
