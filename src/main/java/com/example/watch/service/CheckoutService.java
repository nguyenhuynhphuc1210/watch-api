package com.example.watch.service;

import com.example.watch.dto.request.CheckoutItemDTO;
import com.example.watch.dto.request.CheckoutRequestDTO;
import com.example.watch.dto.response.CheckoutResponseDTO;
import com.example.watch.entity.Order;
import com.example.watch.entity.OrderDetail;
import com.example.watch.entity.Payment;
import com.example.watch.entity.Product;
import com.example.watch.entity.User;
import com.example.watch.enums.OrderStatus;
import com.example.watch.enums.PaymentMethod;
import com.example.watch.enums.PaymentStatus;
import com.example.watch.repository.OrderDetailRepository;
import com.example.watch.repository.OrderRepository;
import com.example.watch.repository.PaymentRepository;
import com.example.watch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderRepository orderRepo;
    private final OrderDetailRepository detailRepo;
    private final PaymentRepository paymentRepo;
    private final ProductRepository productRepo;

    @Transactional
    public CheckoutResponseDTO checkout(
            CheckoutRequestDTO dto,
            User user
    ) {

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 1. Create Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderCode("ORD-" + java.util.UUID.randomUUID());
        order.setShippingAddress(dto.getShippingAddress());
        order.setNote(dto.getNote());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO); // IMPORTANT

        orderRepo.save(order);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderDetail> details = new java.util.ArrayList<>();

        // 2. Create Order Details
        for (CheckoutItemDTO i : dto.getItems()) {

            Product p = productRepo.findById(i.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            BigDecimal itemTotal =
                    p.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()));

            OrderDetail od = new OrderDetail();
            od.setOrder(order);
            od.setProduct(p);
            od.setPrice(p.getPrice()); // snapshot
            od.setQuantity(i.getQuantity());
            od.setTotal(itemTotal);

            detailRepo.save(od);

            details.add(od);
            total = total.add(itemTotal);
        }

        order.setTotalAmount(total);
        order.setOrderDetails(details);

        // 3. Create Payment
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setAmount(total);

        if (dto.getPaymentMethod() == PaymentMethod.COD) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PAID);
        }

        paymentRepo.save(payment);
        orderRepo.save(order);

        return new CheckoutResponseDTO(
                order.getId(),
                order.getOrderCode(),
                payment.getPaymentStatus()
        );
    }
}



