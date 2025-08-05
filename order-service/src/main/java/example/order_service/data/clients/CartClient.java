package example.order_service.data.clients;


import example.order_service.data.dataClases.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/cart/order-info")
    OrderInfo getOrder(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/cart/clear")
    void clearCart(@RequestHeader("X-User-Id") Long userId);
}
