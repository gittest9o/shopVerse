package com.shop.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    private static SecretKey SECRET_KEY;
    private static Duration EXPIRATION_PERIOD;

    private static final String USERID = "userId";

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Value("${jwt.expiration-period}")
    public void setExpirationPeriod(String expirationPeriodString) {
        EXPIRATION_PERIOD = Duration.parse(expirationPeriodString);
    }

    public static String generateToken(Long userId, String email) {
        ZonedDateTime utcZoned = ZonedDateTime.now(ZoneId.of("UTC"));
        Date issuedAt = Date.from(utcZoned.toInstant());
        Date expiration = Date.from(utcZoned.plusSeconds(EXPIRATION_PERIOD.getSeconds()).toInstant());

        return Jwts.builder()
                .setSubject(email)
                .claim(USERID, userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
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
