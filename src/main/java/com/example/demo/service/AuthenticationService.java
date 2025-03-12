package com.example.demo.service;


import com.example.demo.data.entity.Users;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.service.model.CreateUser;
import com.example.demo.service.model.LoginUser;
import com.example.demo.web.exception.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users signup(CreateUser input) {
        System.out.println("signup");

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

