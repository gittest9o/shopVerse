package com.shop.order.service.controllers;

import com.shop.order.service.data.dataClases.Notification;
import com.shop.order.service.services.KafkaProducerService;
import com.shop.order.service.services.OrderService;
import com.shop.order.service.data.clients.CartClient;
import com.shop.order.service.data.clients.UserClient;
import com.shop.order.service.data.dataClases.Order;
import com.shop.order.service.data.dataClases.OrderInfo;
import com.shop.order.service.data.dataClases.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class OrderController {

    private final CartClient cartClient;
    private final UserClient userClient;
    private final OrderService orderService;
    private final KafkaProducerService kafka;



    @GetMapping("/confirm")
    public String confirmedOrder(@RequestHeader("X-User-Id") Long userId,
                                 Model model) {

        OrderInfo orderInfo = cartClient.getOrder(userId);
        cartClient.clearCart(userId);

        UserDto userDto = userClient.getUser(userId);

        Order order = orderService.createOrder(userId,orderInfo.getTotalPrice());

        kafka.sendNotification(
                Notification.builder()
                .orderId(order.getId())
                .email(userDto.getEmail())
                .orderInfo(orderInfo)
                .build());

        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("orderId", order.getId());
        model.addAttribute("user", userDto);

        return "order";
    }
}
