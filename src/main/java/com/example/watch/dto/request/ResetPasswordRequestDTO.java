package com.example.watch.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    private String email;
    private String newPassword;
}
