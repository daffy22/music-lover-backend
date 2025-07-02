package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.User;

import java.util.List;

public interface UserService {
    String login(String email,  String password);

    User createUser(User user);

    String getUserIdFromUsername(String username);

    User getUserByID(String userId);

    List<User> getAllUsers();

    User updateUser(String userId, User userUpdates);

    void deleteUser(String userId);
}
