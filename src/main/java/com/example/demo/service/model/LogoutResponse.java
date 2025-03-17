package com.example.demo.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Accessors(chain = true)
public class LogoutResponse {
    private HttpStatus status;
    private String message;

}
