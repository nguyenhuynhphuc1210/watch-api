package com.example.watch.mapper;

import com.example.watch.dto.response.ProductImageDTO;
import com.example.watch.entity.ProductImage;

public class ProductImageMapper {

    public static ProductImageDTO toDTO(ProductImage image) {
        return new ProductImageDTO(
                image.getId(),
                image.getImageUrl(),
                image.getIsPrimary()
        );
    }
}
