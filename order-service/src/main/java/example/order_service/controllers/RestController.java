package example.order_service.controllers;


import example.order_service.data.OrderRepository;
import example.order_service.data.dataClases.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
