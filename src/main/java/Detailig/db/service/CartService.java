package Detailig.db.service;

import Detailig.db.dto.CartItemRequest;
import Detailig.db.dto.CartResponse;
import Detailig.db.entiti.Cart;
import Detailig.db.entiti.CartItem;
import Detailig.db.entiti.User;
import Detailig.db.entiti.Worker;
import Detailig.db.mapper.CartMapper;
import Detailig.db.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ServiceRepository serviceRepository;
    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartResponse getCartByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> createNewCart(user));
        return cartMapper.toResponse(cart);
    }

    public CartResponse addItem(Long userId, CartItemRequest request) {
        Cart cart = getCartEntityByUser(userId);

        Detailig.db.entiti.Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + request.serviceId()));
        Worker worker = workerRepository.findById(request.workerId())
                .orElseThrow(() -> new EntityNotFoundException("Worker not found with id: " + request.workerId()));

        // Проверка, что работник может выполнять эту услугу
        if (!worker.getServices().contains(service)) {
            throw new IllegalArgumentException("Worker does not provide this service");
        }

        // Проверяем, есть ли уже такая позиция
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(item -> item.getService().equals(service) && item.getWorker().equals(worker))
                .findFirst();

        if (existing.isEmpty()) {
            CartItem newItem = createCartItem(cart, service, worker);
            cart.getItems().add(newItem);
            cartRepository.save(cart);
        }
        // Если позиция уже есть – просто возвращаем корзину без изменений

        return cartMapper.toResponse(cart);
    }

    private CartItem createCartItem(Cart cart, Detailig.db.entiti.Service service, Worker worker) {
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setService(service);
        item.setWorker(worker);
        item.setPriceAtAdd(service.getPrice());
        return item;
    }

    private Cart getCartEntityByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return cartRepository.findByUser(user)
                .orElseGet(() -> createNewCart(user));
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public CartResponse clearCart(Long userId) {
        Cart cart = getCartEntityByUser(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
        return cartMapper.toResponse(cart);
    }

    public CartResponse removeItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));
        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        return cartMapper.toResponse(cart);
    }
}
