package com.example.demo.web.controller;

import com.example.demo.data.entity.Users;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import com.example.demo.service.model.CreateUser;
import com.example.demo.service.model.LoginResponse;
import com.example.demo.service.model.LoginUser;
import com.example.demo.service.model.LogoutResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class.getName());

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Users> register(@RequestBody CreateUser registerUserDto) {
        Users registeredUser = authenticationService.signup(registerUserDto);
        logger.debug("Registered user {}", registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUser loginUserDto) {

        logger.info("Authenticated user {}", loginUserDto.getEmail());
        return this.authenticationService.login(loginUserDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        logger.info("Authenticated user Signed out");
        return authenticationService.signout(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken( @RequestParam UUID refreshToken) {
        logger.info("Authenticated user Refresh token");
        LoginResponse response= authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }


}