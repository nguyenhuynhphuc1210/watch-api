package com.example.watch.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class OrderDetailResponseDTO {

    private Long productId;
    private String productName;

    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;
}
