package com.example.watch.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class OrderRequestDTO {

    private String shippingAddress;
    private String note;
}
