package com.example.watch.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductImageDTO {
    private Long id;
    private String imageUrl;
    private Boolean isPrimary;
}
