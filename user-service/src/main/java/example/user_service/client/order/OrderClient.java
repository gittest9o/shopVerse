package example.user_service.client.order;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/order/api/{userId}")
    List<OrderDto> findOrderByUserId(@PathVariable Long userId);

}
