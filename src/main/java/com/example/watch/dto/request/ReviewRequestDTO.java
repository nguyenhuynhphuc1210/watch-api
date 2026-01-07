package com.example.watch.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ReviewRequestDTO {

    private Long productId;
    private Integer rating;
    private String comment;
}
