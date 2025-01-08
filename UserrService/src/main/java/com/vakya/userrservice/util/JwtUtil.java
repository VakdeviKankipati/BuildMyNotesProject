package com.vakya.userrservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey;

    public JwtUtil(@Value("${jwt.secret:}") String secretKeyFromProperties) {
        if (secretKeyFromProperties.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key is not configured. Ensure it's set in application.properties.");
        }
        this.secretKey = secretKeyFromProperties;
    }

    public String generateToken(String username) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSecretKey() {
        return secretKey;
    }
}
