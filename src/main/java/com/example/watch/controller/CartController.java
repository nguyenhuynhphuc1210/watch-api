package com.example.watch.controller;

import com.example.watch.dto.response.CartItemResponseDTO;
import com.example.watch.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.watch.dto.request.CartItemRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public void addToCart(
            @RequestParam Long userId,
            @RequestBody CartItemRequestDTO dto
    ) {
        cartService.addToCart(userId, dto);
    }

    @GetMapping
    public List<CartItemResponseDTO> getCart(@RequestParam Long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/remove")
    public void remove(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        cartService.removeFromCart(userId, productId);
    }

    @DeleteMapping("/clear")
    public void clear(@RequestParam Long userId) {
        cartService.clearCart(userId);
    }
}

