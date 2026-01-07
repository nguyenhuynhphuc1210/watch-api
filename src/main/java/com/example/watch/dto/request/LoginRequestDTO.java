package com.example.watch.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;
}
