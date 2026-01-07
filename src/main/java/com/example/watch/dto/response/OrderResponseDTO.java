package com.example.watch.dto.response;

import com.example.watch.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private String orderCode;
    private OrderStatus status;

    private String shippingAddress;
    private String note;

    private BigDecimal totalAmount;
    private List<OrderDetailResponseDTO> orderDetails;

    private LocalDateTime createdAt;
}
