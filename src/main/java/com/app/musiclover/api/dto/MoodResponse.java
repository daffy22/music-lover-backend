package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.Mood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoodResponse {
    private Long id;
    private String name;

    public MoodResponse(Mood mood) {
        BeanUtils.copyProperties(mood, this);
    }
}
