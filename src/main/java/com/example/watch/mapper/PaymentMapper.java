package com.example.watch.mapper;

import com.example.watch.dto.request.PaymentRequestDTO;
import com.example.watch.dto.response.PaymentResponseDTO;
import com.example.watch.entity.Order;
import com.example.watch.entity.Payment;
import com.example.watch.enums.PaymentStatus;


public class PaymentMapper {

    public static Payment toEntity(PaymentRequestDTO dto, Order order) {

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalAmount());
        return payment;
    }

    public static PaymentResponseDTO toDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getPaidAt()
        );
    }
}
