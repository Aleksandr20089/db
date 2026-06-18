package Detailig.db.service;

import Detailig.db.dto.OrderResponse;
import Detailig.db.entiti.*;
import Detailig.db.mapper.OrderMapper;
import Detailig.db.repository.CartRepository;
import Detailig.db.repository.OrderRepository;
import Detailig.db.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;


    public OrderResponse createOrderFromCart(Long userId) {
        // 1. Получаем пользователя и корзину
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // 2. Создаём заказ без комментария и прочего
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        // 3. Преобразуем позиции корзины в OrderItem (код остаётся без изменений)
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = orderMapper.toOrder(cartItem);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            orderItem.setStatus(OrderStatus.PENDING);
        }
        order.setItems(orderItems);


        BigDecimal total = orderItems.stream()
                .map(OrderItem::getPriceAtOrder)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);

        // 4. Сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // 5. Очищаем корзину
        cartService.clearCart(userId);

        // 6. Возвращаем DTO
        return orderMapper.toResponse(savedOrder);
    }

    // Получение заказа по ID – возвращает OrderResponse
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toResponse(order);
    }

    public OrderResponse getOrderByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким id: " + userId + " не найден"));
        Order order = orderRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        return orderMapper.toResponse(order);
    }

    // Обновление статуса – возвращает OrderResponse
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        return orderMapper.toResponse(updated);
    }
}

