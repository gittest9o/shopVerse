package com.shop.auth.service.controllers;

import com.shop.auth.service.data.DataClasses.LoginDto;
import com.shop.auth.service.data.DataClasses.UserDTO;
import com.shop.auth.service.JwtUtil;
import com.shop.auth.service.data.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginData", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("loginData") LoginDto loginData,
            BindingResult bindingResult,
            HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        UserDTO userDTO = userService.authenticate(loginData.getEmail(), loginData.getPassword());
        if (userDTO != null) {

            String token = JwtUtil.generateToken(userDTO.getId(), userDTO.getEmail());

            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 день
            response.addCookie(jwtCookie);

            return "redirect:http://localhost:8080/products";

        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());


        return "redirect:http://localhost:8080/products";
    }

}