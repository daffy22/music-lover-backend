package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.Role;
import com.app.musiclover.data.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.UUID;

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

    @NotNull(message = "Role cannot be null.")
    private Role role;

    @NotNull(message = "Active cannot be null.")
    private Boolean active;

    public User toUser() {
        doDefault();
        User user = new User();
        BeanUtils.copyProperties(this, user);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        return user;
    }

    private void doDefault() {
        if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.USER;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }
}
