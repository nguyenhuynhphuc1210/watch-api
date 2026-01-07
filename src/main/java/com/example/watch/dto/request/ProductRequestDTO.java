package com.example.watch.dto.request;

import com.example.watch.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductRequestDTO {

    private String name;
    private Long brandId;
    private Long categoryId;

    private BigDecimal price;
    private Integer stockQuantity;

    private String description;
    private String specification;

    private Gender gender;
    private ProductStatus status;

    private List<String> imageUrls;
}
