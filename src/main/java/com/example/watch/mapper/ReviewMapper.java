package com.example.watch.mapper;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;
import com.example.watch.entity.Product;
import com.example.watch.entity.Review;
import com.example.watch.entity.User;

import java.time.format.DateTimeFormatter;

public class ReviewMapper {

    public static Review toEntity(
            ReviewRequestDTO dto,
            User user,
            Product product
    ) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }

    public static ReviewResponseDTO toDTO(Review review) {

        String createdAt = review.getCreatedAt() != null
                ? review.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : null;

        return new ReviewResponseDTO(
                review.getId(),
                review.getProduct().getId(),     // ✅ productId
                review.getUser().getFullName(),  // ✅ userName
                review.getRating(),
                review.getComment(),
                createdAt                        // ✅ createdAt
        );
    }
}
