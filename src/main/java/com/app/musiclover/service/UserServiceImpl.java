package com.app.musiclover.service;

import com.app.musiclover.data.dao.UserDao;
import com.app.musiclover.data.model.User;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserByID(String userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User updateUser(String userId, User userUpdates) {
        User userToUpdate = getUserByID(userId);
        userToUpdate.update(userUpdates);
        return userDao.save(userToUpdate);
    }

    @Override
    public void deleteUser(String userId) {
        if (!userDao.existsById(userId)) {
            throw new NotFoundException("User " + userId);
        }
        userDao.deleteById(userId);
    }
}
