package com.app.musiclover.data.repository;

import com.app.musiclover.data.model.MusicalPiece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicalPieceRepository extends JpaRepository<MusicalPiece, Long> {
    @Query("""
    SELECT m FROM MusicalPiece m
    WHERE (:era IS NULL OR LOWER(m.era) LIKE LOWER(CONCAT('%', :era, '%')))
      AND (:instrument IS NULL OR LOWER(m.instrument) LIKE LOWER(CONCAT('%', :instrument, '%')))
      AND (:duration IS NULL OR LOWER(m.duration) LIKE LOWER(CONCAT('%', :duration, '%')))
    """)
    List<MusicalPiece> findByFilters(String era, String instrument, Integer duration);

    @Query("""
    SELECT m FROM MusicalPiece m
    WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:era IS NULL OR LOWER(m.era) LIKE LOWER(CONCAT('%', :era, '%')))
      AND (:composer IS NULL OR LOWER(m.composer) LIKE LOWER(CONCAT('%', :composer, '%')))
    """)
    Page<MusicalPiece> findByFilters(
            String title,
            String era,
            String composer,
            Pageable pageable
    );
}
