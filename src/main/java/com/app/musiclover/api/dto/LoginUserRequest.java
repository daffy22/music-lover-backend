package com.app.musiclover.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {
    @NotBlank(message = "Email cannot be null, empty or just spaces.")
    @Email(message = "Must be a valid email format.")
    private String email;

    @NotBlank(message = "Password cannot be null, empty or just spaces.")
    private String password;
}
