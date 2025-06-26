package com.app.musiclover.api.resource;

import com.app.musiclover.api.dto.AuthLoginRequest;
import com.app.musiclover.api.dto.AuthResponse;
import com.app.musiclover.api.dto.CreateUserRequest;
import com.app.musiclover.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationResource.AUTH)
public class AuthenticationResource {

    public static final String AUTH = "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    private final UserService userService;

    public AuthenticationResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(SIGN_IN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest authLoginRequest) {
        return new ResponseEntity<>(userService.loginUser(authLoginRequest), HttpStatus.OK);
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody CreateUserRequest createAuthRequest) {
        return new ResponseEntity<>(userService.registerUser(createAuthRequest), HttpStatus.CREATED);
    }
}
