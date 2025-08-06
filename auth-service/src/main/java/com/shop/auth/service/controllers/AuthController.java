package com.shop.auth.service.controllers;

import com.shop.auth.service.data.DataClasses.LoginDto;
import com.shop.auth.service.data.DataClasses.UserDTO;
import com.shop.auth.service.JwtUtil;
import com.shop.auth.service.data.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginDto loginData,
            HttpServletResponse response) {

        try {
            UserDTO userDTO = userService.authenticate(loginData.getEmail(), loginData.getPassword());
            String token = JwtUtil.generateToken(userDTO.getId(), userDTO.getEmail());

            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(jwtCookie);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("user", userDTO);

            return ResponseEntity.ok(responseBody);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", "");
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }
}