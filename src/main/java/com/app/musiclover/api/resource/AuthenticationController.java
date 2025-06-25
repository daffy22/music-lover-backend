package com.app.musiclover.api.resource;

import com.app.musiclover.api.dto.AuthLoginRequest;
import com.app.musiclover.api.dto.AuthResponse;
import com.app.musiclover.api.dto.CreateAuthRequest;
import com.app.musiclover.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    public static final String TOKEN = "/token";
    public static final String SIGN_UP = "/sign-up";

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthenticationController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest authLoginRequest) {
        return new ResponseEntity<>(userDetailsServiceImpl.loginUser(authLoginRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody CreateAuthRequest createAuthRequest) {
        return new ResponseEntity<>(userDetailsServiceImpl.registerUser(createAuthRequest), HttpStatus.CREATED);
    }
}
