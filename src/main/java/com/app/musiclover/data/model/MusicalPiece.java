package com.app.musiclover.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "musical_pieces")
public class MusicalPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String composer;

    private String era;

    @Column(length = 30)
    private String instrument;

    private Integer duration;

    private String url;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_piece_id")
    private Set<Mood> moodHashSet = new HashSet<>();

    public void update(MusicalPiece musicalPieceRequest) {
        BeanUtils.copyProperties(musicalPieceRequest, this);
    }
}
