package com.example.watch.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserRequestDTO {

    private String fullName;
    private String email;
    private String password;
    private String phone;
}
