package com.example.demo.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    private final HttpStatus status;
    private final Map<String, String> details;

    public ValidationException(HttpStatus status, Map<String, String> details) {
        super("Validation failed: " + details);
        this.status = status;
        this.details = details;
    }

}