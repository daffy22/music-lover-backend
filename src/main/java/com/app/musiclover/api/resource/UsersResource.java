package com.app.musiclover.api.resource;

import com.app.musiclover.api.dto.CreateUserRequest;
import com.app.musiclover.api.dto.UserResponse;
import com.app.musiclover.data.model.User;
import com.app.musiclover.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User userCreated = userService.createUser(createUserRequest.toUser());
        URI location = URI.create(USERS + "/" + userCreated.getId());
        return ResponseEntity.created(location).body(new UserResponse(userCreated));
    }


}
