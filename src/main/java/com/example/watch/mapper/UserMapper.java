package com.example.watch.mapper;

import com.example.watch.dto.request.UserRequestDTO;
import com.example.watch.dto.response.UserResponseDTO;
import com.example.watch.entity.User;
import com.example.watch.enums.UserRole;
import com.example.watch.enums.UserStatus;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus()
        );
    }

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // nhớ encode ở service
        user.setPhone(dto.getPhone());
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }
}
