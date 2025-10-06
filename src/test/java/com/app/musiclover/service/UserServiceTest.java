package com.app.musiclover.service;

import com.app.musiclover.data.model.MusicalPiece;
import com.app.musiclover.data.model.User;
import com.app.musiclover.data.repository.MusicalPieceRepository;
import com.app.musiclover.data.repository.UserRepository;
import com.app.musiclover.domain.exception.BadRequestException;
import com.app.musiclover.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MusicalPieceRepository musicalPieceRepository;

    @Mock
    private AuthUserServiceImpl authUserService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
        // Given
        User user = User.builder()
                .username("lmef")
                .email("lmef@mail.com")
                .password("p123456ABCDEF@")
                .build();

        final String randomUUID = UUID.randomUUID().toString();
        User savedUser = User.builder()
                .id(randomUUID)
                .username("lmef")
                .email("lmef@mail.com")
                .password("encodedPassword")
                .build();

        when(passwordEncoder.encode("p123456ABCDEF@")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = userService.createUser(user);

        // Then
        assertNotNull(result);
        assertEquals(randomUUID, result.getId());
        assertEquals("lmef", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());

        verify(passwordEncoder).encode("p123456ABCDEF@");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUserInvalidEmail() {
        // Given
        final String invalidEmail = "lmefmail.com";
        User user = User.builder()
                .username("lmef")
                .email(invalidEmail)
                .password("C123abc@")
                .build();

        // When & Then
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.createUser(user)
        );

        assertTrue(exception.getMessage().contains("Invalid email format"));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUserInvalidUsername() {
        // Given
        final String invalidUsername = "lu";
        User user = User.builder()
                .username(invalidUsername)
                .email("lmef@mail.com")
                .password("C123abc@")
                .build();

        // When & Then
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.createUser(user)
        );

        assertTrue(exception.getMessage().contains("Username must be between 3 and 20 characters"));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUserInvalidPassword() {
        // Given
        final String invalidPassword = "123456";
        User user = User.builder()
                .username("lmef")
                .email("lmef@mail.com")
                .password(invalidPassword)
                .build();

        // When & Then
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.createUser(user)
        );

        assertTrue(exception.getMessage().contains("Password must be between 12 and 64 characters long"));

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any(String.class));
    }

    @Test
    void testGetUserById() {
        // Given
        final String randomUUID = UUID.randomUUID().toString();
        User savedUser = User.builder()
                .id(randomUUID)
                .username("lmef")
                .email("lmef@mail.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findById(randomUUID)).thenReturn(Optional.of(savedUser));

        // When
        User result = userService.getUserByID(randomUUID);

        // Then
        assertNotNull(result);
        assertEquals(randomUUID, result.getId());
        assertEquals("lmef", result.getUsername());
        assertEquals("lmef@mail.com", result.getEmail());

        verify(userRepository).findById(randomUUID);
    }

    @Test
    void testGetUserByIdNotFound() {
        // Given
        final String randomUUID = UUID.randomUUID().toString();

        when(userRepository.findById(randomUUID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.getUserByID(randomUUID));

        verify(userRepository).findById(randomUUID);
    }

    @Test
    void testGetAllUsers() {
        final String randomUUID = UUID.randomUUID().toString();
        List<User> usersSaved = List.of(User.builder()
                .id(randomUUID)
                .username("lmef")
                .email("lmef@mail.com")
                .password("encodedPassword")
                .build());

        when(userRepository.findAll()).thenReturn(usersSaved);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(randomUUID, result.get(0).getId());
        assertEquals("lmef@mail.com", result.get(0).getEmail());

        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUserName() {
        // Given
        final String randomUUID = UUID.randomUUID().toString();
        final String newUsername = "NEW_USERNAME";

        User userSaved = User.builder()
                .id(randomUUID)
                .username("lmef")
                .email("lmef@mail.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findById(randomUUID)).thenReturn(Optional.of(userSaved));
        when(userRepository.existsByUsername(newUsername)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(userSaved);

        // When
        User result = userService.updateUsername(randomUUID, newUsername);
        assertEquals(randomUUID, result.getId());
        assertEquals(newUsername, result.getUsername());
        assertEquals("lmef@mail.com", result.getEmail());

        verify(userRepository).findById(randomUUID);
        verify(userRepository).existsByUsername(newUsername);
    }

    @Test
    void testDeleteUser() {
        // Given
        final String randomUUID = UUID.randomUUID().toString();

        when(userRepository.existsById(randomUUID)).thenReturn(true);
        doNothing().when(userRepository).deleteById(randomUUID);

        // When
        userService.deleteUser(randomUUID);

        // Then
        verify(userRepository).existsById(randomUUID);
        verify(userRepository).deleteById(randomUUID);
    }

    @Test
    void testAddFavorite() {
        // Given
        final String randomUUID = UUID.randomUUID().toString();
        User user = User.builder()
                .id(randomUUID)
                .username("lmef")
                .email("lmef@mail.com")
                .password("encodedPassword")
                .favoriteMusicalPieces(new HashSet<>())
                .build();

        MusicalPiece musicalPiece = MusicalPiece.builder()
                .id(1L)
                .title("Nocturne in E-flat major Op.9 No.2")
                .composer("Chopin")
                .era("Romantic")
                .instrument("Piano")
                .duration(200)
                .url("https://youtu.be/9E6b3swbnWg?si=GpO3V0_0HWRlEBy8")
                .votes(1000)
                .createdBy("lmef")
                .build();

        // When
        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.of(user));
        when(musicalPieceRepository.findById(1L)).thenReturn(Optional.of(musicalPiece));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addFavorite(1L);

        // Then
        assertTrue(user.getFavoriteMusicalPieces().contains(musicalPiece));
        assertEquals(1, user.getFavoriteMusicalPieces().size());

        verify(userRepository).findByUsername("lmef");
        verify(musicalPieceRepository).findById(1L);
    }

    @Test
    void testRemoveFavorite() {
        // Given
        final String randomUUID = UUID.randomUUID().toString();
        User user = User.builder()
                .id(randomUUID)
                .username("lmef")
                .email("lmef@mail.com")
                .password("encodedPassword")
                .favoriteMusicalPieces(new HashSet<>())
                .build();

        MusicalPiece musicalPiece = MusicalPiece.builder()
                .id(1L)
                .title("Nocturne in E-flat major Op.9 No.2")
                .composer("Chopin")
                .era("Romantic")
                .instrument("Piano")
                .duration(200)
                .url("https://youtu.be/9E6b3swbnWg?si=GpO3V0_0HWRlEBy8")
                .votes(1000)
                .createdBy("lmef")
                .build();

        // When
        when(authUserService.getUsername()).thenReturn("lmef");
        when(userRepository.findByUsername("lmef")).thenReturn(Optional.of(user));
        when(musicalPieceRepository.findById(1L)).thenReturn(Optional.of(musicalPiece));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.deleteFavorite(1L);

        // Then
        assertFalse(user.getFavoriteMusicalPieces().contains(musicalPiece));
        assertEquals(0, user.getFavoriteMusicalPieces().size());

        verify(userRepository).findByUsername("lmef");
        verify(musicalPieceRepository).findById(1L);
    }
}
