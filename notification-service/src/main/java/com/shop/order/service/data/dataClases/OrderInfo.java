package com.shop.order.service.data.dataClases;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderInfo {
    private List<CartItem> items;
    private BigDecimal totalPrice;
}
