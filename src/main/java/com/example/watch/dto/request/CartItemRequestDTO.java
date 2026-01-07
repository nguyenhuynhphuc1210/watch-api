package com.example.watch.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CartItemRequestDTO {

    private Long productId;
    private Integer quantity;
}
