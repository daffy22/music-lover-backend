package com.app.musiclover.service;

import com.app.musiclover.domain.exception.ForbiddenException;
import com.app.musiclover.domain.service.AuthUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Override
    public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ForbiddenException("No authenticated user");
        }
        return auth.getName();
    }
}
