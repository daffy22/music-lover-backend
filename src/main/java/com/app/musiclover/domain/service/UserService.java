package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserByID(String userId);

    List<User> getAllUsers();

    User updateUser(String userId, User userUpdates);

    void deleteUser(String userId);
}
