package com.example.watch.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {

    private Long id;
    private Long productId;
    private String userName;
    private int rating;
    private String comment;
    private String createdAt;
}
