package Detailig.db.controller;

import Detailig.db.dto.OrderResponse;
import Detailig.db.entiti.OrderStatus;
import Detailig.db.entiti.User;
import Detailig.db.security.CustomUserDetails;
import Detailig.db.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Создать заказ из корзины
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@AuthenticationPrincipal CustomUserDetails user) {
        OrderResponse response = orderService.createOrderFromCart(Long.valueOf(user.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Получить заказ по ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@AuthenticationPrincipal CustomUserDetails user,
                                                  @PathVariable Long orderId) {
        // Можно добавить проверку, что заказ принадлежит пользователю
        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    // Обновить статус заказа (для администратора)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long orderId,
                                                      @RequestParam OrderStatus status) {
        OrderResponse response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }
}