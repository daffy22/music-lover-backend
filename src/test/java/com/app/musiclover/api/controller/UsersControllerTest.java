package com.app.musiclover.api.controller;

import com.app.musiclover.api.dto.CreateUserRequest;
import com.app.musiclover.api.dto.LoginUserRequest;
import com.app.musiclover.configuration.JpaAuditingConfig;
import com.app.musiclover.configuration.JwtAuthenticationFilter;
import com.app.musiclover.configuration.SecurityConfig;
import com.app.musiclover.data.model.User;
import com.app.musiclover.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = UsersController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                JpaRepositoriesAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        },
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        SecurityConfig.class,
                        JwtAuthenticationFilter.class,
                        JpaAuditingConfig.class
                }
        )
)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

//    @MockitoBean
//    private AuthUserServiceImpl authUserService;

//    @MockitoBean
//    private JwtServiceImpl jwtServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        // Arrange
        LoginUserRequest request = new LoginUserRequest("user@mail.com", "password123");
        String expectedToken = "jwt.token.here";

        when(userService.login(request.getEmail(), request.getPassword()))
                .thenReturn(expectedToken);

        // Act & Assert
        String uri = UsersController.USERS + UsersController.SIGN_IN;
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken));

        verify(userService).login(request.getEmail(), request.getPassword());
    }

    @Test
    void createUser_ValidRequest_ReturnsCreatedUser() throws Exception {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "newuser",
                "newuser@mail.com",
                "Password123@"
        );

        User createdUser = User.builder()
                .id("123")
                .username("newuser")
                .email("newuser@mail.com")
                .active(true)
                .build();

        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        // Act & Assert
        String uri = UsersController.USERS + UsersController.SIGN_UP;
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("newuser@mail.com"))
                .andDo(print());

        verify(userService).createUser(any(User.class));
    }
}
