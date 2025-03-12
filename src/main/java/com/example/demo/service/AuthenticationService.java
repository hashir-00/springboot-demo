package com.example.demo.service;


import com.example.demo.data.entity.Users;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.service.model.CreateUser;
import com.example.demo.service.model.LoginUser;
import com.example.demo.service.model.LogoutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public  LogoutResponse signout(String token) {
        try {
            jwtService.invalidateToken(token);
            return new LogoutResponse().setMessage("Successfully logged out").setStatus(HttpStatus.OK);
        } catch (Exception e) {
            return new LogoutResponse().setMessage(e.getMessage()).setStatus(HttpStatus.UNAUTHORIZED);
        }
    }

}
