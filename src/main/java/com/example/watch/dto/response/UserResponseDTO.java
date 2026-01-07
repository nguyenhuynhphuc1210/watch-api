package com.example.watch.dto.response;

import com.example.watch.enums.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private UserStatus status;
}
