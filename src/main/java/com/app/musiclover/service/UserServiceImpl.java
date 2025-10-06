package com.app.musiclover.service;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.Role;
import com.app.musiclover.data.repository.MusicalPieceRepository;
import com.app.musiclover.data.repository.UserRepository;
import com.app.musiclover.data.model.User;
import com.app.musiclover.domain.exception.ConflictException;
import com.app.musiclover.domain.exception.NotFoundException;
import com.app.musiclover.domain.service.AuthUserService;
import com.app.musiclover.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final MusicalPieceRepository musicalPieceRepository;
    private final AuthUserService authUserService;

    @Override
    public String login(String email, String password) {
        return userRepository.findByEmail(email)
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
        assertNoExistByUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setActive(true);
        return userRepository.save(user);
    }

    private void assertNoExistByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("The email already exists: " + email);
        }
    }

    private void assertNoExistByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("The username already exists: " + username);
        }
    }

    @Override
    public User getUserByID(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUsername(String userId, String newUsername) {
        User userToUpdate = getUserByID(userId);
        userToUpdate.updateUsername(newUsername);
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void addFavorite(Long musicalPieceId) {
        User user = getUserByUsername(authUserService.getUsername());
        MusicalPiece musicalPiece = getMusicalPieceById(musicalPieceId);
        user.addFavorite(musicalPiece);
        userRepository.save(user);
    }

    @Override
    public void deleteFavorite(Long musicalPieceId) {
        User user = getUserByUsername(authUserService.getUsername());
        MusicalPiece musicalPiece = getMusicalPieceById(musicalPieceId);
        user.removeFavorite(musicalPiece);
        userRepository.save(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private MusicalPiece getMusicalPieceById(Long id) {
        return musicalPieceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Musical piece not found"));
    }

    @Override
    public Set<MusicalPiece> getAllFavoritesByUsername() {
        return userRepository.findFavoritesByUsername(authUserService.getUsername());
    }
}
