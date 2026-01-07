package com.example.watch.service;

import com.example.watch.dto.response.CartItemResponseDTO;
import java.util.List;

public interface CartService {

    void addToCart(Long userId, Long productId, int quantity);

    List<CartItemResponseDTO> getCart(Long userId);

    void removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);
}

