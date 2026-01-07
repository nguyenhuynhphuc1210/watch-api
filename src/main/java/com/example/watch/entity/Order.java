package com.example.watch.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.watch.enums.OrderStatus;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;

    @Column(unique = true, nullable = false)
    private String orderCode;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String shippingAddress;

    @Column(columnDefinition = "TEXT")
    private String note;
}


