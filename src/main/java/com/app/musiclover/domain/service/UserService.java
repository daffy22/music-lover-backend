package com.app.musiclover.domain.service;

import com.app.musiclover.api.dto.AuthLoginRequest;
import com.app.musiclover.api.dto.AuthResponse;
import com.app.musiclover.api.dto.CreateUserRequest;
import com.app.musiclover.data.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity userEntity);

    AuthResponse registerUser(CreateUserRequest createUserRequest);

    AuthResponse loginUser(AuthLoginRequest authLoginRequest);

    UserEntity getUserByID(String userId);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(String userId, UserEntity userEntityUpdates);

    void deleteUser(String userId);
}
