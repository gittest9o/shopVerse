package com.shop.auth.service.controllers;

import com.shop.auth.service.data.DataClasses.UserDTO;
import com.shop.auth.service.data.DataClasses.UserRegistrationRequest;
import com.shop.auth.service.data.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegistrationRequest registrationRequest) {

        // Проверка совпадения паролей
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Пароли не совпадают");
        }

        try {
            UserDTO userDto = new UserDTO();
            userDto.setFirstName(registrationRequest.getFirstName());
            userDto.setLastName(registrationRequest.getLastName());
            userDto.setEmail(registrationRequest.getEmail());
            userDto.setPassword(registrationRequest.getPassword());

            userService.register(userDto);
            return ResponseEntity.status(201).body("Регистрация успешна");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body("Пользователь с таким email уже зарегистрирован");
        }
    }
}
