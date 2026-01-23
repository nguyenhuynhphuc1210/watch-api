package com.example.watch.service.impl;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;
import com.example.watch.entity.Product;
import com.example.watch.entity.Review;
import com.example.watch.entity.User;
import com.example.watch.enums.OrderStatus;
import com.example.watch.mapper.ReviewMapper;
import com.example.watch.repository.ProductRepository;
import com.example.watch.repository.ReviewRepository;
import com.example.watch.repository.UserRepository;
import com.example.watch.repository.OrderRepository;
import com.example.watch.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public ReviewResponseDTO create(Long userId, ReviewRequestDTO dto) {

        // 1. Kiểm tra đã mua & COMPLETED chưa
        boolean hasBought = orderRepository.hasCompletedOrderWithProduct(
                userId,
                OrderStatus.COMPLETED,
                dto.getProductId());

        if (!hasBought) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bạn chưa mua sản phẩm này");
        }

        // 2. Kiểm tra đã đánh giá chưa
        boolean reviewed = reviewRepository.existsByUserIdAndProductId(
                userId,
                dto.getProductId());

        if (reviewed) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bạn đã đánh giá sản phẩm này");
        }

        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(dto.getProductId()).orElseThrow();

        Review review = ReviewMapper.toEntity(dto, user, product);
        return ReviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getByProduct(Long productId) {
        return reviewRepository
                .findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(ReviewMapper::toDTO)
                .toList();
    }

}
