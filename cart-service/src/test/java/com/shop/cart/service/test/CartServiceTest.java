package com.shop.cart.service.test;

import com.shop.cart.service.data.CartItem;
import com.shop.cart.service.data.OrderInfo;
import com.shop.cart.service.services.CartService;
import com.shop.cart.service.services.ProductClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private HashOperations<String, Object, Object> hashOperations;
    @Mock
    private ProductClient productClient;
    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        lenient().when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void addToCart_shouldPutItemInRedis() {
        cartService.addToCart(1L, "100", 2);

        verify(hashOperations).put("cart:1", "100", "2");
    }

    @Test
    void getCart_shouldReturnCartFromRedis() {
        Map<Object, Object> mockCart = new HashMap<>();
        mockCart.put("101", "3");

        when(hashOperations.entries("cart:5")).thenReturn(mockCart);

        Map<Object, Object> result = cartService.getCart(5L);

        assertEquals(1, result.size());
        assertEquals("3", result.get("101"));
    }

    @Test
    void updateQuantity_shouldUpdateQuantity_whenGreaterThanZero() {
        cartService.updateQuantity(2L, "200", 5);

        verify(hashOperations).put("cart:2", "200", "5");
    }

    @Test
    void updateQuantity_shouldRemoveItem_whenZeroOrNegative() {
        cartService.updateQuantity(2L, "200", 0);

        verify(hashOperations, never()).put(any(), any(), any());
        verify(hashOperations).delete("cart:2", "200");
    }

    @Test
    void removeFromCart_shouldDeleteItemFromRedis() {
        cartService.removeFromCart(3L, "300");

        verify(hashOperations).delete("cart:3", "300");
    }

    @Test
    void clearCart_shouldDeleteCartKey() {
        cartService.clearCart(4L);

        verify(redisTemplate).delete("cart:4");
    }

    @Test
    void getOrderInfo_shouldBuildOrderDetailsCorrectly() {
        Map<Object, Object> mockCart = new HashMap<>();
        mockCart.put("10", "2"); // productId -> quantity

        when(hashOperations.entries("cart:7")).thenReturn(mockCart);

        CartItem mockProduct = new CartItem();
        mockProduct.setId(10L);
        mockProduct.setName("Test Product");
        mockProduct.setPrice(new BigDecimal("50.00"));
        mockProduct.setImageUrl("img.jpg");

        when(productClient.getProductById(10L)).thenReturn(mockProduct);

        OrderInfo orderInfo = cartService.getOrderInfo(7L);

        assertEquals(1, orderInfo.getItems().size());
        assertEquals(new BigDecimal("100.00"), orderInfo.getTotalPrice());

        CartItem item = orderInfo.getItems().get(0);
        assertEquals(Short.parseShort("2"), item.getQuantity());
        assertEquals("Test Product", item.getName());
    }
}
