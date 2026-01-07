package com.example.watch.mapper;

import com.example.watch.dto.request.ProductRequestDTO;
import com.example.watch.dto.response.ProductResponseDTO;
import com.example.watch.entity.Brand;
import com.example.watch.entity.Category;
import com.example.watch.entity.Product;

import java.util.stream.Collectors;

public class ProductMapper {

    /**
     * Entity → DTO
     */
    public static ProductResponseDTO toDTO(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),

                product.getBrand() != null ? product.getBrand().getId() : null,
                product.getBrand() != null ? product.getBrand().getName() : null,

                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,

                product.getPrice(),
                product.getStockQuantity(),

                product.getDescription(),
                product.getSpecification(),

                product.getGender(),
                product.getStatus(),

                product.getImages()
                        .stream()
                        .map(ProductImageMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    /**
     * DTO → Entity (CREATE)
     */
    public static Product toEntity(
            ProductRequestDTO dto,
            Brand brand,
            Category category
    ) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        product.setDescription(dto.getDescription());
        product.setSpecification(dto.getSpecification());

        product.setGender(dto.getGender());
        product.setStatus(dto.getStatus());

        return product;
    }

    /**
     * DTO → Entity (UPDATE)
     */
    public static void updateEntity(
            Product product,
            ProductRequestDTO dto,
            Brand brand,
            Category category
    ) {
        product.setName(dto.getName());
        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        product.setDescription(dto.getDescription());
        product.setSpecification(dto.getSpecification());

        product.setGender(dto.getGender());
        product.setStatus(dto.getStatus());
    }
}
