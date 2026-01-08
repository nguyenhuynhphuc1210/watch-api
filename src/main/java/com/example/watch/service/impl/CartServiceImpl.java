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
import com.example.watch.dto.request.CartItemRequestDTO;

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
    public void addToCart(Long userId, CartItemRequestDTO dto) {

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // (Optional) check tồn kho
        if (product.getStockQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }

        CartItem cartItem = cartItemRepository
                .findByUserIdAndProductId(userId, dto.getProductId())
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(dto.getQuantity());
        }

        cartItemRepository.save(cartItem);
    }

    // ================= GET CART =================
    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponseDTO> getCart(Long userId) {

        return cartItemRepository.findByUserId(userId)
                .stream()
                .map(CartItemMapper::toDTO)
                .toList();
    }

    // ================= REMOVE ONE PRODUCT =================
    @Override
    public void removeFromCart(Long userId, Long productId) {

        if (!cartItemRepository
                .findByUserIdAndProductId(userId, productId)
                .isPresent()) {
            throw new RuntimeException("Cart item not found");
        }

        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    // ================= CLEAR CART =================
    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}

