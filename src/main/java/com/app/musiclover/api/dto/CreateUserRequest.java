package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

public class CreateUserRequest {

    @NotBlank(message = "Username cannot be null, empty or just spaces.")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters long.")
    private String username;

    @NotBlank(message = "Must be an email")
    @Size(max = 100, message = "Email must have a maximum of 100 characters.")
    private String email;

    @NotBlank(message = "Password cannot be null, empty or just spaces.")
    @Size(min = 12, max = 64, message = "Password must be between 12 and 64 characters long.")
    private String password;

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
