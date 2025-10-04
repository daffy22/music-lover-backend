package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_name", nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 100, unique = true,
            columnDefinition = "VARCHAR(255) CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$')")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<RecommendationHistory> recommendationHistoriesArrayList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorite_musical_pieces",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "musical_piece_id")
    )
    private Set<MusicalPiece> favoriteMusicalPieces = new HashSet<>();

    public void updateUsername(String newUserName) {
        setUsername(newUserName);
    }

    public void addRecommendationHistory(RecommendationHistory recommendationHistory) {
        recommendationHistoriesArrayList.add(recommendationHistory);
    }

    public void addFavorite(MusicalPiece musicalPiece) {
        this.favoriteMusicalPieces.add(musicalPiece);
    }

    public void removeFavorite(MusicalPiece musicalPiece) {
        this.favoriteMusicalPieces.remove(musicalPiece);
    }
}
