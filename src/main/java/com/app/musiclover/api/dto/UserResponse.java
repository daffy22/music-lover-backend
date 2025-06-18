package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.User;
import org.springframework.beans.BeanUtils;

public class UserResponse {
    private String id;
    private String username;
    private String email;

    public UserResponse(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
