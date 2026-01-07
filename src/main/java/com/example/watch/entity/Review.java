package com.example.watch.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    // Bạn nên dùng @Min(1) và @Max(5) nếu có Bean Validation
    private Integer rating; 

    @Column(columnDefinition = "TEXT")
    private String comment; // Có thể null
}
