package com.example.watch.dto.response;

import com.example.watch.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CheckoutResponseDTO {

    private Long orderId;
    private String orderCode;
    private PaymentStatus paymentStatus;
}

