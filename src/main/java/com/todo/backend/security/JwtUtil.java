package com.todo.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${todo.jwt.secret}")
    private String secret;

    @Value("${todo.jwt.expiration}")
    private Long expiration;

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secret));
    }

    public void test(String token ) {
        String x = JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getSubject();
    }
}
