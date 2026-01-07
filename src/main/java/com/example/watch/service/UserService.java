package com.example.watch.service;

import com.example.watch.dto.request.UserRequestDTO;
import com.example.watch.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO create(UserRequestDTO dto);

    UserResponseDTO update(Long id, UserRequestDTO dto);

    void delete(Long id);

    UserResponseDTO getById(Long id);

    List<UserResponseDTO> getAll();

    UserResponseDTO getCurrentUser(); // ⭐ /me
}
