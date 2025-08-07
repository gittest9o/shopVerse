package com.shop.cart.service.controllers;
import com.shop.cart.service.data.OrderInfo;
import com.shop.cart.service.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/cart")
public class RestController {

    private final CartService cartService;


    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("productId") String productId
    ) {
        cartService.addToCart(userId, productId, 1);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<String> updateCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("productId") String productId,
            @RequestParam int quantity
    ) {
        cartService.updateQuantity(userId, productId, quantity);
        return ResponseEntity.ok("Количество обновлено");
    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<String> removeCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("productId") String productId
    ) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok("Товар удален из корзины");
    }

    @GetMapping("/order-info")
    public OrderInfo getCart(@RequestHeader("X-User-Id") Long userId) {
        return cartService.getOrderInfo(userId);
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

}

