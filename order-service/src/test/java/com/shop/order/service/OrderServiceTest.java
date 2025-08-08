package com.shop.order.service;

import com.shop.order.service.data.OrderRepository;
import com.shop.order.service.data.dataClases.Order;
import com.shop.order.service.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_shouldSaveAndReturnOrder() {
        Long userId = 1L;
        BigDecimal totalPrice = new BigDecimal("150.00");

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(userId, totalPrice);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(totalPrice, result.getTotalPrice());
        assertNotNull(result.getCreatedAt());


        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(captor.capture());

        Order savedOrder = captor.getValue();
        assertEquals(userId, savedOrder.getUserId());
        assertEquals(totalPrice, savedOrder.getTotalPrice());
        assertNotNull(savedOrder.getCreatedAt());
    }
}


