package com.shop.cart.service.controllers;

import com.shop.cart.service.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;


    @GetMapping
    public String getCart(@RequestHeader("X-User-Id") Long userId, Model model) {
        var orderInfo = cartService.getOrderInfo(userId);

        model.addAttribute("cartItems", orderInfo.getItems());
        model.addAttribute("totalPrice", orderInfo.getTotalPrice());

        return "cart";
    }
}
