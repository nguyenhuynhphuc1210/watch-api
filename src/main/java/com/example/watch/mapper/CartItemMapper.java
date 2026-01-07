package com.example.watch.mapper;

import com.example.watch.dto.response.CartItemResponseDTO;
import com.example.watch.entity.CartItem;
import com.example.watch.entity.ProductImage;

import java.math.BigDecimal;

public class CartItemMapper {

    public static CartItemResponseDTO toDTO(CartItem item) {

        String primaryImage = item.getProduct()
                .getImages()
                .stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);

        BigDecimal total = item.getProduct()
                .getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                primaryImage,
                item.getProduct().getPrice(),
                item.getQuantity(),
                total
        );
    }
}
