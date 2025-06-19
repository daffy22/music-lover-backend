package com.app.musiclover.api.resource;

import com.app.musiclover.api.dto.CreateUserRequest;
import com.app.musiclover.api.dto.UserResponse;
import com.app.musiclover.data.model.User;
import com.app.musiclover.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UsersResource.USERS)
public class UsersResource {

    private final UserService userService;

    static final String USERS = "/api/v1/users";
    static final String USER_ID = "/{userId}";

    public UsersResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User userCreated = userService.createUser(createUserRequest.toUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(userCreated));
    }

    @GetMapping(USER_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) {
        User userById = userService.getUserByID(userId);
        return ResponseEntity.ok(new UserResponse(userById));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers().stream()
                .map(UserResponse::new)
                .toList();
        return ResponseEntity.ok(userResponseList);
    }

    @PutMapping(USER_ID)
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userId,
                                                   @Valid @RequestBody CreateUserRequest updateUserRequest) {
        User userUpdated = userService.updateUser(userId, updateUserRequest.toUser());
        return ResponseEntity.ok(new UserResponse(userUpdated));
    }

    @DeleteMapping(USER_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
