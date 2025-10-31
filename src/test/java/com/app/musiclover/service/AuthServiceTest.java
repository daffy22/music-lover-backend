package com.app.musiclover.service;

import com.app.musiclover.domain.exception.ForbiddenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthUserServiceImpl authUserService;

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetUserName_AuthenticatedUser_ReturnsUsername() {
        // Arrange
        String expectedUsername = "lmef";

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(expectedUsername);

        // Act
        String result = authUserService.getUsername();

        // When
        assertNotNull(result);
        assertEquals(expectedUsername, result);

        verify(authentication).isAuthenticated();
        verify(authentication).getName();
    }

    @Test
    void getUsername_NotAuthenticated_ThrowsForbiddenException() {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () ->
                authUserService.getUsername()
        );

        assertTrue(exception.getMessage().contains("Forbidden Exception"));
        verify(authentication).isAuthenticated();
        verify(authentication, never()).getName();
    }
}
