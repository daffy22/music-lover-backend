package com.app.musiclover.domain.exception;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request";

    public BadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
