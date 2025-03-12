package com.example.demo.service;


import com.example.demo.data.entity.Users;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.service.model.CreateUser;
import com.example.demo.service.model.LoginUser;

import com.example.demo.service.model.LogoutResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.web.exception.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final   JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Users signup(CreateUser input) {
        Users user = new Users()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public Users authenticate(LoginUser input) {
        try {
            // Authenticate the user using Spring Security's AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (Exception e) {
            // Throw a custom exception if authentication fails
            throw new AuthenticationException("Invalid email or password");
        }

        // Retrieve the user from the database
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));
    }
    }

    public  ResponseEntity<LogoutResponse> signout(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (!authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String token = authorizationHeader.substring(7).trim();
            jwtService.invalidateToken(token);
            return ResponseEntity.ok(new LogoutResponse().setMessage("You have been successfully logged out").setStatus(HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LogoutResponse().setMessage(e.getMessage()).setStatus(HttpStatus.UNAUTHORIZED));
        }
    }

}

