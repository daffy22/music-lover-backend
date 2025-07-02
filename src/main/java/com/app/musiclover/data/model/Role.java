package com.app.musiclover.data.model;

public enum Role {
    ADMIN, USER, INVITED;

    public static final String PREFIX = "ROLE_";

    public static Role of(String withPrefix) {
        return Role.valueOf(withPrefix.replace(Role.PREFIX, ""));
    }
}
