package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "moods")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String name;

    @Column(name = "created_by")
    private String createdBy;

    public void update(Mood updateMoodRequest) {
        setName(updateMoodRequest.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mood mood = (Mood) o;
        return Objects.equals(id, mood.id) && Objects.equals(name, mood.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
