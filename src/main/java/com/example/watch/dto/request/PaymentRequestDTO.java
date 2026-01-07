package com.example.watch.dto.request;

import com.example.watch.enums.PaymentMethod;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PaymentRequestDTO {

    private Long orderId;
    private PaymentMethod paymentMethod;
}
