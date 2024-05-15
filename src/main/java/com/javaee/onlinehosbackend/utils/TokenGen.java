package com.javaee.onlinehosbackend.utils;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

// 生成Token令牌的类
public class TokenGen {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int TOKEN_LENGTH = 20;
    private final ConcurrentHashMap<String, String> tokens = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> tokenGenerateTimes = new ConcurrentHashMap<>();

    public TokenGen()
    {

    }

    public String generateToken(String userId) {
        Random random = new Random();
        StringBuilder tokenBuilder;
        String token;

        do {
            tokenBuilder = new StringBuilder(TOKEN_LENGTH);
            for (int i = 0; i < TOKEN_LENGTH; i++) {
                char c = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
                tokenBuilder.append(c);
            }
            token = tokenBuilder.toString();
        } while (tokens.containsKey(userId + "_" + token));

        tokens.put(userId + "_" + token, token);
        tokenGenerateTimes.put(userId + "_" + token, System.currentTimeMillis());

        return token;
    }

    public boolean verifyToken(String userId, String token, long expirationTime) {
        String fullToken = userId + "_" + token;
        Long tokenTime = tokenGenerateTimes.get(fullToken);
        System.out.println(tokenGenerateTimes);
        if (tokenTime != null) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - tokenTime) <= expirationTime) {
                // Token is valid
                tokenGenerateTimes.put(fullToken, currentTime); // Update last used time
                return true;
            } else {
                // Token expired
                tokens.remove(fullToken);
                tokenGenerateTimes.remove(fullToken);
            }
        }
        return false;
    }
}
