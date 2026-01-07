package com.example.watch.service.impl;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;
import com.example.watch.entity.Product;
import com.example.watch.entity.Review;
import com.example.watch.entity.User;
import com.example.watch.mapper.ReviewMapper;
import com.example.watch.repository.ProductRepository;
import com.example.watch.repository.ReviewRepository;
import com.example.watch.repository.UserRepository;
import com.example.watch.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResponseDTO create(Long userId, ReviewRequestDTO dto) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(dto.getProductId()).orElseThrow();

        Review review = ReviewMapper.toEntity(dto, user, product);
        return ReviewMapper.toDTO(reviewRepository.save(review));
    }
}

