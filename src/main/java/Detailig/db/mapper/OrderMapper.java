package Detailig.db.mapper;

import Detailig.db.dto.OrderItemResponse;
import Detailig.db.dto.OrderResponse;
import Detailig.db.entiti.CartItem;
import Detailig.db.entiti.Order;
import Detailig.db.entiti.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderDate", source = "createdAt")
    OrderResponse toResponse(Order order);

    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "workerName", source = "worker.name")
    @Mapping(target = "workerLastName", source = "worker.lastName")
    @Mapping(target = "workerSurName", source = "worker.surName")
    @Mapping(target = "totalPrice", source = "priceAtOrder") // = priceAtOrder
    OrderItemResponse toItemResponse(OrderItem item);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "priceAtOrder", source = "priceAtAdd")
    @Mapping(target = "status", ignore = true)
    OrderItem toOrder(CartItem cartItem);

}