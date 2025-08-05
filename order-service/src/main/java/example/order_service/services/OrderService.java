package example.order_service.services;

import example.order_service.data.OrderRepository;
import example.order_service.data.dataClases.Order;
import example.order_service.data.dataClases.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    @Transactional
    public Order createOrder(Long userId, BigDecimal totalPrice) {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setUserId(userId);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }
}
