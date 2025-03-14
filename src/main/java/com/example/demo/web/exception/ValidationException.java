package com.example.demo.web.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private final HttpStatus status;
    private final Map<String, String> details;

    public ValidationException(HttpStatus status, Map<String, String> details) {
        super("Validation failed: " + details);
        this.status = status;
        this.details = details;
    }

}