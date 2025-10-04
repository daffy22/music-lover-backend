package com.app.musiclover.data.repository;

import com.app.musiclover.data.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, Long> {
}
