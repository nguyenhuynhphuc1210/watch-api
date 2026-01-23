package com.example.watch.controller;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;
import com.example.watch.security.CustomUserDetails;
import com.example.watch.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Tạo đánh giá sản phẩm
     * - Chỉ cho phép user đã mua sản phẩm
     * - Mỗi user chỉ đánh giá 1 lần / product
     */
    @PostMapping
    public ReviewResponseDTO createReview(
            @AuthenticationPrincipal CustomUserDetails cud,
            @RequestBody ReviewRequestDTO dto) {
        return reviewService.create(
                cud.getUser().getId(),
                dto);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getByProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok(
                reviewService.getByProduct(productId));
    }

}
