package com.example.api.exception;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException() {
        this("Unauthorized user.");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }

}