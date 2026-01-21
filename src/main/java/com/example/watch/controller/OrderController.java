package com.example.watch.controller;

import com.example.watch.dto.response.OrderResponseDTO;
import com.example.watch.entity.Order;

import com.example.watch.enums.OrderStatus;
import com.example.watch.mapper.OrderMapper;
import com.example.watch.repository.OrderRepository;
import com.example.watch.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    /**
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
             @AuthenticationPrincipal CustomUserDetails cud
    ) {
        List<Order> orders =
                orderRepository.findByUserIdOrderByCreatedAtDesc(cud.getUser().getId());

        List<OrderResponseDTO> response = orders.stream()
                .map(OrderMapper::toDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/orders/{orderCode}
     */
    @GetMapping("/{orderCode}")
    public ResponseEntity<OrderResponseDTO> getOrderDetail(
            @PathVariable String orderCode,
            @AuthenticationPrincipal CustomUserDetails cud
    ) {
        Order order = orderRepository
                .findByOrderCodeAndUser_Id(orderCode, cud.getUser().getId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return ResponseEntity.ok(OrderMapper.toDTO(order));
    }

    /**
     * PUT /api/orders/{orderCode}/cancel
     */
    @PutMapping("/{orderCode}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable String orderCode,
             @AuthenticationPrincipal CustomUserDetails cud
    ) {
        Order order = orderRepository
                .findByOrderCodeAndUser_Id(orderCode, cud.getUser().getId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            return ResponseEntity.badRequest()
                    .body("Không thể hủy đơn ở trạng thái hiện tại");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return ResponseEntity.ok().build();
    }
}
