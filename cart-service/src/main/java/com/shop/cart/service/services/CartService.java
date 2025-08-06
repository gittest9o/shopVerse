package com.shop.cart.service.services;

import com.shop.cart.service.data.CartItem;
import com.shop.cart.service.data.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductClient productClient;


    private String cartKey(Long userId) {
        return "cart:" + userId;
    }

    public void addToCart(Long userId, String productId, int quantity) {
        redisTemplate.opsForHash().put(cartKey(userId), productId, String.valueOf(quantity));
    }

    public Map<Object, Object> getCart(Long userId) {
        return redisTemplate.opsForHash().entries(cartKey(userId));
    }

    public void updateQuantity(Long userId, String productId, int quantity) {
        if (quantity <= 0) {
            removeFromCart(userId, productId);
        } else {
            redisTemplate.opsForHash().put(cartKey(userId), productId, String.valueOf(quantity));
        }
    }

    public void removeFromCart(Long userId, String productId) {
        redisTemplate.opsForHash().delete(cartKey(userId), productId);
    }

    public void clearCart(Long userId) {
        redisTemplate.delete(cartKey(userId));
    }


    public OrderInfo getOrderInfo(Long userId) {
        Map<Object, Object> rawCart = getCart(userId);
        List<CartItem> cartItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Object, Object> entry : rawCart.entrySet()) {
            String productId = (String) entry.getKey();
            Short quantity = Short.parseShort(entry.getValue().toString());


            CartItem product = productClient.getProductById(Long.parseLong(productId));

            if (product != null) {
                CartItem cartItem = new CartItem();
                cartItem.setId(Long.parseLong(productId));
                cartItem.setName(product.getName());
                cartItem.setPrice(product.getPrice());
                cartItem.setImageUrl(product.getImageUrl());
                cartItem.setQuantity(quantity);

                cartItems.add(cartItem);
                totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setTotalPrice(totalPrice);
        orderInfo.setItems(cartItems);
        return orderInfo;
    }
}

