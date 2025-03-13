package com.example.demo.web.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class GlobalExceptionHandler  {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {


        // TODO send this stack trace to an observability tool
        logger.error("Unhandled exception occurred: {}", exception.getMessage(), exception);

        ProblemDetail errorDetail;

        if (exception instanceof BadCredentialsException) {
            errorDetail = createProblemDetail(HttpStatus.UNAUTHORIZED, "The username or password is incorrect", exception);
        } else if (exception instanceof AccountStatusException) {
            errorDetail = createProblemDetail(HttpStatus.FORBIDDEN, "The account is locked", exception);
        } else if (exception instanceof AccessDeniedException) {
            errorDetail = createProblemDetail(HttpStatus.FORBIDDEN, "You are not authorized to access this resource", exception);
        } else if (exception instanceof SignatureException) {
            errorDetail = createProblemDetail(HttpStatus.FORBIDDEN, "The JWT signature is invalid", exception);
        } else if (exception instanceof ExpiredJwtException) {
            errorDetail = createProblemDetail(HttpStatus.FORBIDDEN, "The JWT token has expired", exception);
        } else {
            // Default case for unhandled exceptions
            errorDetail = createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown internal server error", exception);
        }

        return errorDetail;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {


        // Log the error message
        logger.error("Error: {}", ex.getMessage());

        // Return the error response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String description, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setProperty("description", description);
        return problemDetail;
    }

}