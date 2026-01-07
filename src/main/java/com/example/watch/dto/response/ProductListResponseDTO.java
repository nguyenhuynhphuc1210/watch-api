package com.example.watch.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductListResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String primaryImage;
    private String brandName;
}
