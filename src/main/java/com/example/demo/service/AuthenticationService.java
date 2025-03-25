package com.example.demo.service;

import com.example.demo.data.entity.RefreshToken;
import com.example.demo.data.entity.Users;
import com.example.demo.data.repository.RefreshTokenRepository;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.service.model.CreateUser;
import com.example.demo.service.model.LoginResponse;
import com.example.demo.service.model.LoginUser;
import com.example.demo.service.model.LogoutResponse;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  private final TemporalAmount refreshTokenTtlString;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenRepository refreshTokenRepository;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      RefreshTokenRepository refreshTokenRepository,
      @Value("${app.refresh-token.ttl}") String refreshTokenTtlString) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.refreshTokenRepository = refreshTokenRepository;
    this.refreshTokenTtlString = Duration.parse("P" + refreshTokenTtlString);
    logger.info("AuthenticationService initialized successfully.");
  }

  // Signup Method
  public Users signup(CreateUser input) {
    logger.debug("Attempting to create a new user with email: {}", input.getEmail());
    try {
      Users user =
          new Users()
              .setFullName(input.getFullName())
              .setEmail(input.getEmail())
              .setPassword(passwordEncoder.encode(input.getPassword()));

      Users savedUser = userRepository.save(user);
      logger.info("User created successfully with email: {}", input.getEmail());
      return savedUser;
    } catch (Exception e) {
      logger.error(
          "Error occurred while creating user with email {}: {}", input.getEmail(), e.getMessage());
      return null;
    }
  }

  // Login Method
  public ResponseEntity<LoginResponse> login(LoginUser loginUserDto) {
    logger.debug("Attempting to authenticate user with email: {}", loginUserDto.getEmail());
    try {
      Users authenticatedUser = this.authenticate(loginUserDto);

      String jwtToken = jwtService.generateToken(authenticatedUser);
      logger.info(
          "JWT token generated successfully for user with email: {}", loginUserDto.getEmail());

      RefreshToken refreshToken = new RefreshToken();
      refreshToken.setUser(authenticatedUser);
      refreshToken.setExpiresAt(Instant.now().plus(refreshTokenTtlString));
      refreshTokenRepository.save(refreshToken);
      logger.info(
          "Refresh token generated successfully for user with email: {}", loginUserDto.getEmail());

      LoginResponse loginResponse =
          new LoginResponse()
              .setToken(jwtToken)
              .setExpiresIn(jwtService.getExpirationTime())
              .setRefreshToken(refreshToken.getId());

      logger.info("Login successful for user with email: {}", loginUserDto.getEmail());
      return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    } catch (Exception e) {
      logger.error(
          "Login failed for user with email {}: {}", loginUserDto.getEmail(), e.getMessage());
      throw new AuthenticationException(e.getMessage());
    }
  }

  public LoginResponse refreshToken(UUID refreshToken) {
    final var refreshTokenEntity =
        refreshTokenRepository
            .findByIdAndExpiresAtAfter(refreshToken, Instant.now())
            .orElseThrow(
                () ->
                    new ValidationException(
                        HttpStatus.BAD_REQUEST,
                        Map.of("refreshToken", "Invalid or expired refresh token")));

    final var newAccessToken = jwtService.generateToken(refreshTokenEntity.getUser());

    return new LoginResponse().setToken(newAccessToken).setRefreshToken(refreshToken);
  }

  public void revokeRefreshToken(int userId) {
    logger.debug("Revoking refresh tokens for user with id {}", userId);

    // Delete all refresh tokens for the user
    refreshTokenRepository.deleteByUserId(userId);
  }

  // Authenticate Method
  public Users authenticate(LoginUser input) {
    logger.debug("Authenticating user with email: {}", input.getEmail());
    try {
      // Authenticate the user using Spring Security's AuthenticationManager
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
      logger.info("Authentication successful for user with email: {}", input.getEmail());
    } catch (Exception e) {
      throw new AuthenticationException("Invalid email or password");
    }

    // Retrieve the user from the database
    return userRepository
        .findByEmail(input.getEmail())
        .orElseThrow(
            () -> {
              logger.warn("User not found with email: {}", input.getEmail());
              return new AuthenticationException("User not found");
            });
  }

  // Signout Method
  @Transactional
  public ResponseEntity<LogoutResponse> signout(HttpServletRequest request) {
    logger.debug("Attempting to log out user");
    try {
      String authorizationHeader = request.getHeader("Authorization");

      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        logger.warn("Invalid or missing Authorization header during logout");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String token = authorizationHeader.substring(7).trim();
      logger.debug("Invalidating token during logout");

      String userName = jwtService.invalidateToken(token);
      Users user =
          this.userRepository
              .findByEmail(userName)
              .orElseThrow(() -> new AuthenticationException("User not found"));
      revokeRefreshToken(user.getId());

      logger.debug("User logged out successfully");

      return ResponseEntity.ok(
          new LogoutResponse()
              .setMessage("You have been successfully logged out")
              .setStatus(HttpStatus.OK));
    } catch (Exception e) {
      logger.error("Error occurred during logout: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new LogoutResponse().setMessage(e.getMessage()).setStatus(HttpStatus.UNAUTHORIZED));
    }
  }
}
