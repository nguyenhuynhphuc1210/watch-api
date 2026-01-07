package com.example.watch.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ReviewResponseDTO {

    private Long id;

    private Long userId;
    private String userName;

    private Integer rating;
    private String comment;
}
