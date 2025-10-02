package com.shop.order.service.data.dataClases;

import lombok.Data;

@Data
public class Notification {
    private Long orderId;
    private String email;
    private OrderInfo orderInfo;
}
