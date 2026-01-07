package com.example.watch.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.watch.enums.PaymentMethod;
import com.example.watch.enums.PaymentStatus;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Payment extends BaseEntity { // Nên kế thừa BaseEntity để có thời gian tạo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING; // Mặc định là đang chờ

    @Column(unique = true) // Mã giao dịch từ cổng thanh toán nên là duy nhất
    private String transactionId;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    private LocalDateTime paidAt;
}


