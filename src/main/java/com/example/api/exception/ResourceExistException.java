package com.example.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceExistException extends RuntimeException {
    private String code;

    public ResourceExistException() {
        super("This email address is already in use. Please use another email address.");
    }

    public ResourceExistException(String msg) {
        super(msg);
    }

    public ResourceExistException(String msg, String code) {
        super(msg);
        this.code = code;
    }

}