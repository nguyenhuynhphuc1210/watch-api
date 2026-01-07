package com.example.watch.mapper;

import com.example.watch.dto.request.ReviewRequestDTO;
import com.example.watch.dto.response.ReviewResponseDTO;
import com.example.watch.entity.Product;
import com.example.watch.entity.Review;
import com.example.watch.entity.User;

public class ReviewMapper {

    public static Review toEntity(ReviewRequestDTO dto, User user, Product product) {

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }

    public static ReviewResponseDTO toDTO(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getFullName(),
                review.getRating(),
                review.getComment()
        );
    }
}
