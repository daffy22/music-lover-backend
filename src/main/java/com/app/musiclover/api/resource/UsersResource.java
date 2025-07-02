package com.app.musiclover.api.resource;

import com.app.musiclover.api.dto.CreateUserRequest;
import com.app.musiclover.api.dto.LoginUserRequest;
import com.app.musiclover.api.dto.TokenDto;
import com.app.musiclover.api.dto.UserResponse;
import com.app.musiclover.data.model.User;
import com.app.musiclover.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UsersResource.USERS)
public class UsersResource {

    private final UserService userService;

    static final String USERS = "/api/v1/users";
    static final String SIGN_IN = "/sign-in";
    static final String SIGN_UP = "/sign-up";
    static final String USER_ID = "/{userId}";

    public UsersResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(SIGN_IN)
    public TokenDto login(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        String token = userService.login(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        return new TokenDto(token);
    }


    @PostMapping(SIGN_UP)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new UserResponse(userService.createUser(createUserRequest.toUser()));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(USER_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) {
        User userById = userService.getUserByID(userId);
        return ResponseEntity.ok(new UserResponse(userById));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers().stream()
                .map(UserResponse::new)
                .toList();
        return ResponseEntity.ok(userResponseList);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(USER_ID)
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userId,
                                                   @Valid @RequestBody CreateUserRequest updateUserRequest) {
        User userUpdated = userService.updateUser(userId, updateUserRequest.toUser());
        return ResponseEntity.ok(new UserResponse(userUpdated));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(USER_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
