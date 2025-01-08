package com.vakya.userrservice;

import com.vakya.userrservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        // Initialize with default secret key for testing
        jwtUtil = new JwtUtil("eW15UzNjcjN0SzN5VzF0aCRwZWMhQGxDaGFyc0FuZDEyM05lcnM");
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "testUser";

        // Generate token
        String token = jwtUtil.generateToken(username);
        assertNotNull(token, "Token should not be null");

        // Validate token
        Claims claims = jwtUtil.validateToken(token);
        assertEquals(username, claims.getSubject(), "Username should match the token subject");
    }


}
