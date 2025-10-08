package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.*;

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
}
