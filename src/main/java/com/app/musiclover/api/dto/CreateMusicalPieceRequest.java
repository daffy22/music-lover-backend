package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.MusicalPiece;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMusicalPieceRequest {

    @NotBlank(message = "Title cannot be null, empty or just spaces.")
    @Size(max = 100, message = "Title must have a maximum of 20 characters.")
    private String title;

    @NotBlank(message = "Composer cannot be null, empty or just spaces.")
    @Size(max = 100, message = "Composer must have a maximum of 20 characters.")
    private String composer;

    @NotBlank(message = "Era cannot be null, empty or just spaces.")
    @Size(max = 50, message = "Era must have a maximum of 20 characters.")
    private String era;

    @NotBlank(message = "Instrument cannot be null, empty or just spaces.")
    @Size(max = 50, message = "Instrument must have a maximum of 20 characters.")
    private String instrument;

    @Positive(message = "Duration cannot be zero.")
    @NotNull(message = "Duration cannot be null.")
    private Integer duration;

    @NotBlank(message = "Url cannot be null, empty or just spaces.")
    @Size(max = 200, message = "Url must have a maximum of 200 characters.")
    private String url;

    public MusicalPiece toMusicalRequest() {
        MusicalPiece musicalPiece = new MusicalPiece();
        BeanUtils.copyProperties(this, musicalPiece);
        return musicalPiece;
    }
}
