package com.example.watch.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class BrandRequestDTO {
    private String name;
    private String country;
    private String description;
    private String logoUrl;
}
