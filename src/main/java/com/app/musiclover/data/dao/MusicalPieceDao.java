package com.app.musiclover.data.dao;

import com.app.musiclover.data.model.MusicalPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicalPieceDao extends JpaRepository<MusicalPiece, String> {
    @Query("""
    SELECT m FROM MusicalPiece m
    WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:composer IS NULL OR LOWER(m.composer) LIKE LOWER(CONCAT('%', :composer, '%')))
      AND (:era IS NULL OR LOWER(m.era) LIKE LOWER(CONCAT('%', :era, '%')))
    """)
    List<MusicalPiece> searchByFilters(String title,
                                       String composer,
                                       String era);
}
