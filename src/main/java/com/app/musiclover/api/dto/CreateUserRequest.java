package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Username cannot be null, empty or just spaces.")
    @Size(max = 20, message = "Username must have a maximum of 20 characters.")
    private String username;

    @NotBlank(message = "Email cannot be null, empty or just spaces.")
    @Email(message = "Must be a valid email format.")
    @Size(max = 100, message = "Email must have a maximum of 100 characters.")
    private String email;

    @NotBlank(message = "Password cannot be null, empty or just spaces.")
    @Size(min = 12, max = 64, message = "Password must be between 12 and 64 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
