package com.example.watch.service;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;
import java.util.List;

public interface ReviewService {
    ReviewResponseDTO create(Long userId, ReviewRequestDTO dto);

    List<ReviewResponseDTO> getByProduct(Long productId);
}
