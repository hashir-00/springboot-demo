package com.example.demo.exception;

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

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail;


        switch (exception) {
            case BadCredentialsException badCredentialsException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("description", "The username or password is incorrect");

                return errorDetail;
            }
            case AccountStatusException accountStatusException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "The account is locked");
                return errorDetail;
            }
            case AccessDeniedException accessDeniedException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "You are not authorized to access this resource");
                return errorDetail;
            }
            case SignatureException signatureException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "The JWT signature is invalid");
                return errorDetail;
            }
            case ExpiredJwtException expiredJwtException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "The JWT token has expired");
                return errorDetail;
            }
            default -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
                errorDetail.setProperty("description", "Unknown internal server error.");

            }
        }



        return errorDetail;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthenticationException(AuthenticationException ex) {
        // Log the error message
        logger.error("Error: {}", ex.getMessage());

        // Create a ProblemDetail response
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setProperty("description", ex.getMessage());

        // Return the error response as JSON
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }
}