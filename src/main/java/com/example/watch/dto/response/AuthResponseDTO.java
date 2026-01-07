package com.example.watch.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AuthResponseDTO {

    private String token;
    private UserResponseDTO user;
}
