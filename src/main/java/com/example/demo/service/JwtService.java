package com.example.demo.service;

import com.example.demo.utils.TokenBlackList;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    // Extract Username from Token
    public String extractUsername(String token) {
        try {
            logger.debug("Extracting username from token");
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            logger.error("Error extracting username from token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract username from token");
        }
    }

    // Extract a Specific Claim from Token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            logger.debug("Extracting claim from token");
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            logger.info("Error extracting claim from token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract claim from token");
        }
    }

    // Generate Token without Extra Claims
    public String generateToken(UserDetails userDetails) {
        try {
            logger.debug("Generating token for user: {}", userDetails.getUsername());
            return generateToken(new HashMap<>(), userDetails);
        } catch (Exception e) {
            logger.error("Error generating token for user {}: {}", userDetails.getUsername(), e.getMessage());
            throw new RuntimeException("Failed to generate token");
        }
    }

    // Generate Token with Extra Claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        try {
            logger.info("Generating token with extra claims for user: {}", userDetails.getUsername());
            return buildToken(extraClaims, userDetails, jwtExpiration);
        } catch (Exception e) {
            logger.error("Error generating token with extra claims for user {}: {}", userDetails.getUsername(), e.getMessage());
            throw new RuntimeException("Failed to generate token with extra claims");
        }
    }

    // Validate Token
    public boolean isValidToken(String token) {
        try {
            logger.debug("Validating token");
            if (TokenBlackList.isTokenBlacklisted(token)) {
                logger.warn("Token is blacklisted");
                return false; // Token is invalid if blacklisted
            }

            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            logger.info("Token is valid");
            return true; // Token is valid
        } catch (Exception e) {
            logger.error("Invalid token: {}", e.getMessage());
            return false; // Token is invalid if parsing fails
        }
    }

    // Invalidate Token
    public void invalidateToken(String token) {
        try {
            logger.debug("Attempting to invalidate token");
            if (TokenBlackList.isTokenBlacklisted(token)) {
                logger.warn("Token is already blacklisted");
                throw new RuntimeException("Token is already blacklisted");
            }
            TokenBlackList.invalidateToken(token);
            logger.info("Token successfully invalidated and blacklisted");
        } catch (Exception e) {
            logger.error("Error invalidating token: {}", e.getMessage());
            throw new RuntimeException("Failed to invalidate token" );
        }
    }

    // Build Token
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        try {
            logger.debug("Building token for user: {} with expiration: {} ms", userDetails.getUsername(), expiration);
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            logger.error("Error building token for user {}: {}", userDetails.getUsername(), e.getMessage());
            throw new RuntimeException("Failed to build token");
        }
    }

    // Check if Token is Valid for a Specific User
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            logger.debug("Checking if token is valid for user: {}", userDetails.getUsername());
            if (TokenBlackList.isTokenBlacklisted(token)) {
                logger.warn("Token is blacklisted for user: {}", userDetails.getUsername());
                return false; // Token is invalid if blacklisted
            }
            final String username = extractUsername(token);
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            if (isValid) {
                logger.info("Token is valid for user: {}", userDetails.getUsername());
            } else {
                logger.warn("Token is invalid or expired for user: {}", userDetails.getUsername());
            }
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating token for user {}: {}", userDetails.getUsername(), e.getMessage());
            return false;
        }
    }

    // Check if Token is Expired
    private boolean isTokenExpired(String token) {
        try {
            logger.debug("Checking if token is expired");
            Date expirationDate = extractExpiration(token);
            boolean isExpired = expirationDate.before(new Date());
            if (isExpired) {
                logger.warn("Token is expired. Expiration date: {}", expirationDate);
            } else {
                logger.info("Token is not expired. Expiration date: {}", expirationDate);
            }
            return isExpired;
        } catch (Exception e) {
            logger.error("Error checking token expiration: {}", e.getMessage());
            return true; // Assume token is expired if an error occurs
        }
    }

    // Extract Expiration Date from Token
    private Date extractExpiration(String token) {
        try {
            logger.debug("Extracting expiration date from token");
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            logger.error("Error extracting expiration date from token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract expiration date");
        }
    }

    public long getExpirationTime() {
        logger.debug("Fetching JWT expiration time: {} ms", jwtExpiration);
        return jwtExpiration;
    }
    // Extract All Claims from Token
    private Claims extractAllClaims(String token) {
        try {
            logger.debug("Extracting all claims from token");
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error extracting claims from token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract claims");
        }
    }

    // Get Signing Key
    private Key getSignInKey() {
        try {
            logger.debug("Decoding secret key for signing");
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            logger.error("Error decoding secret key: {}", e.getMessage());
            throw new RuntimeException("Failed to decode secret key");
        }
    }
}