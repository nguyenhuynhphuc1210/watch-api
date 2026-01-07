package com.example.watch.dto.response;

import com.example.watch.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PaymentResponseDTO {

    private Long id;
    private Long orderId;

    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime paidAt;
}
