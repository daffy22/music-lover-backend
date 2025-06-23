package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class CreateUserRequest {

    @NotBlank(message = "Username cannot be null, empty or just spaces.")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters long.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, digits, and underscores.")
    private String username;

    @NotBlank(message = "Email cannot be null, empty or just spaces.")
    @Email(message = "Must be a valid email format.")
    @Size(max = 100, message = "Email must have a maximum of 100 characters.")
    private String email;

//    @NotBlank(message = "Password cannot be null, empty or just spaces.")
//    @Size(min = 12, max = 64, message = "Password must be between 12 and 64 characters long.")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
