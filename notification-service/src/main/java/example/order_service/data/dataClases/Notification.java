package example.order_service.data.dataClases;

import lombok.Data;

@Data
public class Notification {
    private Long orderId;
    private String email;
    private OrderInfo orderInfo;
}
