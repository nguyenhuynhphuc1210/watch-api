package com.example.watch.controller;

import com.example.watch.dto.response.CartItemResponseDTO;
import com.example.watch.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ================= ADD TO CART =================
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    // ================= GET CART =================
    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCart(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // ================= REMOVE PRODUCT =================
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    // ================= CLEAR CART =================
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            @RequestParam Long userId
    ) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}
