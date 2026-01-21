package com.example.watch.service.impl;

import com.example.watch.dto.request.UserRequestDTO;
import com.example.watch.dto.response.UserResponseDTO;
import com.example.watch.entity.User;
import com.example.watch.mapper.UserMapper;
import com.example.watch.repository.UserRepository;
import com.example.watch.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO getById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ⭐⭐⭐ /me
    @Override
    public UserResponseDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // username = email

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO updateCurrentUser(UserRequestDTO dto) {

        // lấy user đang đăng nhập từ SecurityContext
        User current = getCurrentUserEntity();

        // cập nhật các field được phép
        current.setFullName(dto.getFullName());
        current.setPhone(dto.getPhone());

        // nếu password không trống → hash lại
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            current.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // ❌ KHÔNG cập nhật email
        // current.setEmail(dto.getEmail());

        userRepository.save(current);

        return UserMapper.toDTO(current);
    }

    private User getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // email là username đăng nhập

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
