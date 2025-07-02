package com.app.musiclover.domain.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractToken(String bearer);

    String createToken(UserDetails userDetails);

    String username(String authorization);

    String role(String authorization);
}
