package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "musical_pieces")
public class MusicalPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String composer;

    @Column(length = 20, nullable = false)
    private String era;

    @Column(length = 30, nullable = false)
    private String instrument;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer votes = 0;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "musical_piece_moods",
            joinColumns = @JoinColumn(name = "musical_piece_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    private Set<Mood> moodHashSet = new HashSet<>();

    public void update(MusicalPiece musicalPieceRequest) {
        setTitle(musicalPieceRequest.getTitle());
        setComposer(musicalPieceRequest.getComposer());
        setEra(musicalPieceRequest.getEra());
        setInstrument(musicalPieceRequest.getInstrument());
        setDuration(musicalPieceRequest.getDuration());
        setUrl(musicalPieceRequest.getUrl());
    }

    public void addMood(Mood mood) {
        moodHashSet.add(mood);
    }

    public void removeMood(Mood mood) {
        moodHashSet.remove(mood);
    }
}
