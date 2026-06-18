package Detailig.db.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        String serviceName,
        BigDecimal servicePrice, // цена на момент добавления
        Long workerId,
        String workerName,
        String workerLastName,
        String workerSurName,
        BigDecimal totalPrice
) {

}
