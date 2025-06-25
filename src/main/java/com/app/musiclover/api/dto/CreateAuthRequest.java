package com.app.musiclover.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateAuthRequest
        (@NotBlank String username, @NotBlank String password, @Valid CreateRoleRequest createRoleRequest) {
}
