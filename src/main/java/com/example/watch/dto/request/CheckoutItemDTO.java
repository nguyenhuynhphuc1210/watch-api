package com.example.watch.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutItemDTO {

    private Long productId;
    private Integer quantity;
}
