package Detailig.db.mapper;

import Detailig.db.dto.CartItemResponse;
import Detailig.db.dto.CartResponse;
import Detailig.db.dto.ImageDto;
import Detailig.db.entiti.Cart;
import Detailig.db.entiti.CartItem;
import Detailig.db.image.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring"  , uses = ImageMapper.class)
public interface CartMapper {
    @Mapping(target = "totalAmount", expression = "java(calculateTotal(cart))")
    CartResponse toResponse(Cart cart);

    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "servicePrice", source = "priceAtAdd")
    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerName", source = "worker.name")
    @Mapping(target = "workerLastName", source = "worker.lastName")
    @Mapping(target = "workerSurName", source = "worker.surName")
    @Mapping(target = "totalPrice", source = "priceAtAdd")
    @Mapping(target = "serviceImages", source = "service.images")
    @Mapping(target = "workerAvatar", source = "worker.image")
    CartItemResponse toItemResponse(CartItem item);




    default BigDecimal calculateTotal(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return cart.getItems().stream()
                .map(CartItem::getPriceAtAdd)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
