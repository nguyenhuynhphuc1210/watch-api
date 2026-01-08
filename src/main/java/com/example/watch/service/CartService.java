package com.example.watch.service;

import com.example.watch.dto.response.CartItemResponseDTO;
import java.util.List;
import com.example.watch.dto.request.CartItemRequestDTO;

public interface CartService {

    void addToCart(Long userId, CartItemRequestDTO dto);

    List<CartItemResponseDTO> getCart(Long userId);

    void removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);
}


