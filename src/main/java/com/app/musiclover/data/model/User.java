package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "musical_piece_id")
    )
    private List<MusicalPiece> favoritesArrayList = new ArrayList<>();

    private List<RecommendationHistory> recommendationHistoriesArrayList = new ArrayList<>();

    public void update(User userUpdates) {
        username = userUpdates.getUsername();
        email = userUpdates.getEmail();
        password = userUpdates.getPassword();
    }
}
