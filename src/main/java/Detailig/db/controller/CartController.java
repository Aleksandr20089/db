package Detailig.db.controller;

import Detailig.db.dto.CartItemRequest;
import Detailig.db.dto.CartResponse;
import Detailig.db.entiti.User;
import Detailig.db.security.CustomUserDetails;
import Detailig.db.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(cartService.getCartByUser(Long.valueOf(user.getUsername())));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(@RequestBody @Valid CartItemRequest request,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(cartService.addItem(Long.valueOf(user.getUsername()), request));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(itemId));
    }

    @DeleteMapping
    public ResponseEntity<CartResponse> clearCart(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(cartService.clearCart(Long.valueOf(user.getUsername())));
    }
}
