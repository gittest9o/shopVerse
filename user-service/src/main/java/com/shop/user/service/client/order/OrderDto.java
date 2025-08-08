package com.shop.user.service.client.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
