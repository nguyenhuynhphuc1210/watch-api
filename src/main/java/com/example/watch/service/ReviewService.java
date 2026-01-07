package com.example.watch.service;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;

public interface ReviewService {
    ReviewResponseDTO create(Long userId, ReviewRequestDTO dto);
}
