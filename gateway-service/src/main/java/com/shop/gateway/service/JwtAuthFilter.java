package com.shop.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private final String SECRET = "LZy72p8GDNBu9qxWAKfXfEF9U6qUQXt3";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        // не требует авторизации
        boolean isPublicPath = path.equals("/") || path.startsWith("/auth") || path.startsWith("/products");

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        } else {
            HttpCookie cookie = exchange.getRequest().getCookies().getFirst("jwt");
            if (cookie != null) {
                jwt = cookie.getValue();
            }
        }


        if (jwt == null) {


            if (isPublicPath) {
                return chain.filter(exchange);
            } else {

                exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                exchange.getResponse().getHeaders().setLocation(URI.create("/auth/login"));
                return exchange.getResponse().setComplete();
            }
        }

        try {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            Long userId = claims.get("userId", Long.class);
            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            //  токен некорректен
            if (isPublicPath) {
                return chain.filter(exchange);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                exchange.getResponse().getHeaders().setLocation(URI.create("/auth/login"));
                return exchange.getResponse().setComplete();
            }
        }
    }

}


