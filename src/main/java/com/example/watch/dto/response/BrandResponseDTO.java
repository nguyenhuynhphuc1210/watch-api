package com.example.watch.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class BrandResponseDTO {
    private Long id;
    private String name;
    private String country;
    private String description;
    private String logoUrl;
}
