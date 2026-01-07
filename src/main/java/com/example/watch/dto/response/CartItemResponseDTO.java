package com.example.watch.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CartItemResponseDTO {

    private Long id;

    private Long productId;
    private String productName;
    private String productImage;

    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;
}
