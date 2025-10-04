package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.Mood;
import jakarta.validation.constraints.NotBlank;
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
public class CreateMoodRequest {

    @NotBlank(message = "Name cannot be null, empty or just spaces.")
    @Size(max = 50, message = "Name must have a maximum of 50 characters.")
    private String name;

    public Mood toMood() {
        Mood mood = new Mood();
        BeanUtils.copyProperties(this, mood);
        return mood;
    }
}
