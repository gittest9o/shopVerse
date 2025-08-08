package com.shop.auth.service.controllers;

import com.shop.auth.service.data.DataClasses.UserDTO;
import com.shop.auth.service.data.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final UserService userService;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userDTO") UserDTO userDto,
            BindingResult bindingResult,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        // совпадения паролей
        if (!userDto.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }


        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            userService.register(userDto);
            return "redirect:http://localhost:8080/products";
        } catch (DataIntegrityViolationException e) {

            model.addAttribute("emailError", "Пользователь с таким email уже зарегистрирован");
            return "registration";
        }
    }
}
