package com.app.musiclover.data.repository;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("SELECT u.favoriteMusicalPieces FROM User u WHERE u.username = :username")
    Set<MusicalPiece> findFavoritesByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
