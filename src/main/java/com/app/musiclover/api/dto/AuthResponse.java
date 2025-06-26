package com.app.musiclover.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String username;
    private String message;
    private String jwt;
    boolean status;
}
