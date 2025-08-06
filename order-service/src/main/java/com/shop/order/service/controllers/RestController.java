package com.shop.order.service.controllers;


import com.shop.order.service.data.OrderRepository;
import com.shop.order.service.data.dataClases.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/order/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final OrderRepository orderRepository;

    @GetMapping("/{userId}")
    public List<Order> findAll(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);

    }

}
