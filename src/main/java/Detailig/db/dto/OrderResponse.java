package Detailig.db.dto;

import Detailig.db.entiti.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        LocalDateTime orderDate ,
        OrderStatus status ,
        BigDecimal totalAmount,
        List<OrderItemResponse> items
) {
}
