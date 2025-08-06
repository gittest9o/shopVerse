package com.shop.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import io.jsonwebtoken.Claims;

public class JwtUtil {

    private static final String SECRET = "LZy72p8GDNBu9qxWAKfXfEF9U6qUQXt3";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(Long userId, String email) {
        ZonedDateTime utcZoned = ZonedDateTime.now(ZoneId.of("UTC"));
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(Date.from(utcZoned.toInstant()))
                .setExpiration(Date.from(utcZoned.plusDays(1).toInstant()))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
