package Detailig.db.dto;

import Detailig.db.entiti.OrderStatus;

public record OrderStatusUpdateRequest (
        OrderStatus status
){
}
