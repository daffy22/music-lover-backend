package com.app.musiclover.domain.service;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    String login(String email,  String password);

    User createUser(User user);

    User getUserByID(String userId);

    List<User> getAllUsers();

    User updateUsername(String userId, String newUserName);

    void deleteUser(String userId);

    void addFavorite(String username, Long musicalPieceId);

    void deleteFavorite(String username, Long musicalPieceId);

    Set<MusicalPiece> getAllFavoritesByUsername(String username);
}
