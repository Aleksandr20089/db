package Detailig.db.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemResponse(
        String serviceName,
        BigDecimal priceAtOrder, // зафиксированная цена
        String workerName,
        String workerLastName,
        String workerSurName,
        BigDecimal totalPrice,
        List<ImageDto> serviceImages,
        ImageDto workerAvatar
) {
}
