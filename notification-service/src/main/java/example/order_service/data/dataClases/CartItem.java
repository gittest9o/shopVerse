package example.order_service.data.dataClases;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private short quantity;
}
