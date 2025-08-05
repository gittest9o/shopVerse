package example.order_service.data.dataClases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private Long orderId;
    private String email;
    private OrderInfo orderInfo;
}
