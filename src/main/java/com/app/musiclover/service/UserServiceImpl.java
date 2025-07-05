package com.app.musiclover.service;

import com.app.musiclover.data.dao.UserDao;
import com.app.musiclover.data.model.User;
import com.app.musiclover.domain.exception.ConflictException;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(String email, String password) {
        return userDao.findByEmail(email)
                .map(user -> {
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        throw new BadCredentialsException("Bad Credentials");
                    }
                    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .roles(user.getRole().name())
                            .build();
                    return  jwtServiceImpl.createToken(userDetails);
                })
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    }

    @Override
    public User createUser(User user) {
        assertNoExistByEmail(user.getEmail());
        return userDao.save(user);
    }

    @Cacheable(value = "usersIdsByUsername", key = "#username", unless = "#result == null")
    @Override
    public String getUserIdFromUsername(String username) {
        return userDao.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private void assertNoExistByEmail(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            throw new ConflictException("The email already exists: " + email);
        }
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
