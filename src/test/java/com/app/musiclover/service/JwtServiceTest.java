package com.app.musiclover.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtServiceImpl jwtService;

    private static final String TEST_SECRET = "myTestSecretKeyForJwtMustBeLongEnough123456789";
    private static final String TEST_ISSUER = "test-music-lover";
    private static final int TEST_EXPIRE = 3600; // 1 hour

    private final String TOKEN_EXAMPLE = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl(TEST_SECRET, TEST_ISSUER, TEST_EXPIRE);
    }

    @Test
    void testExtractToken_ValidTokenWithBearer_RemovesBearerPrefix() {
        // Arrange
        String token = TOKEN_EXAMPLE;

        // Act
        String result = jwtService.extractToken(token);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.split("\\.").length);
        assertFalse(result.contains("Bearer"));
        assertTrue(result.startsWith("eyJ"));
    }

    @Test
    void testExtractToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.jwt.token";

        // Act
        String result = jwtService.extractToken(invalidToken);

        // Assert
        assertNotNull(result);
        assertEquals(Strings.EMPTY, result);
    }

    @Test
    void testCreateToken_ValidUserDetails_ReturnsValidJwt() {
        // Arrange
        UserDetails userDetails = createUserDetails("testUser", "ROLE_USER");

        // Act
        String result = jwtService.createToken(userDetails);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.split("\\.").length);

        DecodedJWT decodedJWT = JWT.decode(result);
        assertEquals(TEST_ISSUER, decodedJWT.getIssuer());
        assertNotNull(decodedJWT.getClaim("username").asString());
        assertEquals("testUser", decodedJWT.getClaim("username").asString());
        assertEquals("ROLE_USER", decodedJWT.getClaim("role").asString());
    }

    @Test
    void username_ValidToken_ReturnsUsername() {
        // Arrange
        UserDetails userDetails = createUserDetails("john.doe", "ROLE_USER");
        String token = jwtService.createToken(userDetails);

        // Act
        String username = jwtService.username(token);

        // Assert
        assertEquals("john.doe", username);
    }

    @Test
    void username_InvalidToken_ThrowsException() {
        // Arrange
        String invalidToken = "invalid.jwt.token";

        // Act & Assert
        assertThrows(JWTVerificationException.class, () ->
                jwtService.username(invalidToken)
        );
    }

    @Test
    void username_TokenWithWrongSignature_ThrowsException() {
        // Arrange
        JwtServiceImpl differentSecretService = new JwtServiceImpl(
                "differentSecret123456789",
                TEST_ISSUER,
                TEST_EXPIRE
        );
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String tokenWithDifferentSecret = differentSecretService.createToken(userDetails);

        // Act & Assert
        assertThrows(JWTVerificationException.class, () ->
                jwtService.username(tokenWithDifferentSecret)
        );
    }

    @Test
    void username_ExpiredToken_ThrowsException() {
        // Arrange
        JwtServiceImpl expiredTokenService = new JwtServiceImpl(
                TEST_SECRET,
                TEST_ISSUER,
                -1
        );
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = expiredTokenService.createToken(userDetails);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Act & Assert
        assertThrows(JWTVerificationException.class, () ->
                jwtService.username(token)
        );
    }

    @Test
    void role_ValidToken_ReturnsRole() {
        // Arrange
        UserDetails userDetails = createUserDetails("testuser", "ROLE_ADMIN");
        String token = jwtService.createToken(userDetails);

        // Act
        String result = jwtService.role(token);

        // Assert
        assertNotNull(result);
        assertEquals("ADMIN", result);
    }

    private UserDetails createUserDetails(String username, String role) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                if (role == null || role.isEmpty()) {
                    return List.of();
                }
                return List.of(new SimpleGrantedAuthority(role));
            }

            @Override
            public String getPassword() {
                return "password123";
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
