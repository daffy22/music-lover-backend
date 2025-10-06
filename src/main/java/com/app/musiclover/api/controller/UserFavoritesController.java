package com.app.musiclover.api.controller;

import com.app.musiclover.api.dto.MusicalPieceResponse;
import com.app.musiclover.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(UserFavoritesController.USER_FAVORITES)
public class UserFavoritesController {

    static final String USER_FAVORITES = "/api/v1/user-favorites";
    static final String FAVORITE_ID = "/{favoriteId}";
    private final UserService userService;

    public UserFavoritesController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(FAVORITE_ID)
    public ResponseEntity<Void> addFavorite(@PathVariable Long favoriteId) {
        userService.addFavorite(favoriteId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(FAVORITE_ID)
    public ResponseEntity<Void> removeFavorite(@PathVariable Long favoriteId) {
        userService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<MusicalPieceResponse>> getAllFavoritesByUsername() {
        Set<MusicalPieceResponse> response = userService.getAllFavoritesByUsername()
                .stream()
                .map(MusicalPieceResponse::new)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }
}
