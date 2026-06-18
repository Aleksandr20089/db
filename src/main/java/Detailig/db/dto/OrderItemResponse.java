package Detailig.db.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        String serviceName,
        BigDecimal priceAtOrder, // зафиксированная цена
        String workerName,
        String workerLastName,
        String workerSurName,
        BigDecimal totalPrice
) {
}
