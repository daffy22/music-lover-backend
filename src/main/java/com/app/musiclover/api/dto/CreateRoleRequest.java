package com.app.musiclover.api.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record CreateRoleRequest
        (@Size(max = 3, message = "The user cannot have more than 3 roles") List<String> roleListName) {
}
