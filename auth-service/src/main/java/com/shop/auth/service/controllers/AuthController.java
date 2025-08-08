package com.shop.auth.service.controllers;

import com.shop.auth.service.data.DataClasses.LoginDto;
import com.shop.auth.service.data.DataClasses.UserDTO;
import com.shop.auth.service.JwtUtil;
import com.shop.auth.service.data.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginData, HttpServletResponse response) {
        UserDTO userDTO = userService.authenticate(loginData.getEmail(), loginData.getPassword());

        if(userDTO != null){
            String token = JwtUtil.generateToken(userDTO.getId(), userDTO.getEmail());

            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 день
            response.addCookie(jwtCookie);

            return ResponseEntity.ok().body("Login successful");

        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().body("Logged out");
    }
}