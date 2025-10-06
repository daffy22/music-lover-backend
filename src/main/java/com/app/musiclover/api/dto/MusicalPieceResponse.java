package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.MusicalPiece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MusicalPieceResponse {

    private Long id;
    private String title;
    private String composer;
    private String era;
    private String instrument;
    private Integer duration;
    private String url;

    public MusicalPieceResponse(MusicalPiece musicalPiece) {
        BeanUtils.copyProperties(musicalPiece, this);
    }
}
