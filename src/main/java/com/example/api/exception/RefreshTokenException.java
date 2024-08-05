package com.example.api.exception;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException() {
        this("refresh token error.");
    }

    public RefreshTokenException(String message) {
        super(message);
    }

}