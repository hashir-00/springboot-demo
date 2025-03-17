package com.example.demo.utils;


import java.util.HashSet;
import java.util.Set;

public class TokenBlackList {

    private final static Set<String> blacklistedTokens = new HashSet<>();

    private TokenBlackList() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Add a token to the blacklist
    public static void invalidateToken(String token) {
        blacklistedTokens.add(token);
    }

    // Check if a token is blacklisted
    public static boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
