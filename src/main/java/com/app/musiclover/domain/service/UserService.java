package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity userEntity);

    UserEntity getUserByID(String userId);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(String userId, UserEntity userEntityUpdates);

    void deleteUser(String userId);
}
