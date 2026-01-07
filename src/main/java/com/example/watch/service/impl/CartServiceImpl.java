package com.example.watch.service.impl;

import com.example.watch.dto.response.CartItemResponseDTO;
import com.example.watch.entity.CartItem;
import com.example.watch.entity.Product;
import com.example.watch.entity.User;
import com.example.watch.mapper.CartItemMapper;
import com.example.watch.repository.CartItemRepository;
import com.example.watch.repository.ProductRepository;
import com.example.watch.repository.UserRepository;
import com.example.watch.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // ================= ADD TO CART =================
    @Override
    public void addToCart(Long userId, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByUser_IdAndProduct_Id(userId, productId)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUser(user);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);

        cartItemRepository.save(cartItem);
    }

    // ================= GET CART =================
    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponseDTO> getCart(Long userId) {

        return cartItemRepository.findByUser_Id(userId)
                .stream()
                .map(CartItemMapper::toDTO)
                .toList();
    }

    // ================= REMOVE ONE PRODUCT =================
    @Override
    public void removeFromCart(Long userId, Long productId) {

        CartItem cartItem = cartItemRepository
                .findByUser_IdAndProduct_Id(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }

    // ================= CLEAR CART =================
    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUser_Id(userId);
    }
}
