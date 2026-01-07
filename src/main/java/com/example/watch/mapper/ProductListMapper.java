package com.example.watch.mapper;

import com.example.watch.dto.response.ProductListResponseDTO;
import com.example.watch.entity.Product;
import com.example.watch.entity.ProductImage;

public class ProductListMapper {

    public static ProductListResponseDTO toDTO(Product product) {

        String primaryImage = product.getImages()
                .stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);

        return new ProductListResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                primaryImage,
                product.getBrand() != null ? product.getBrand().getName() : null
        );
    }
}
