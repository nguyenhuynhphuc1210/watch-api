package com.example.watch.dto.response;

import com.example.watch.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductResponseDTO  {

    private Long id;
    private String name;

    private Long brandId;
    private String brandName;

    private Long categoryId;
    private String categoryName;

    private BigDecimal price;
    private Integer stockQuantity;

    private String description;
    private String specification;

    private Gender gender;
    private ProductStatus status;

    private List<ProductImageDTO> images;
}
