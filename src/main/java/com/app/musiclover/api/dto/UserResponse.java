package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;

    public UserResponse(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
