package com.example.api.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token is invalid!");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
