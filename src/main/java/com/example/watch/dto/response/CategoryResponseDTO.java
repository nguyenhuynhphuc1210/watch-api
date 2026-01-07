package com.example.watch.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
}
