package com.example.watch.service;

import com.example.watch.dto.response.OrderResponseDTO;
import java.util.List;
import com.example.watch.enums.OrderStatus;

public interface OrderService {

    List<OrderResponseDTO> getOrdersByUser(Long userId);

    OrderResponseDTO getByOrderCode(String orderCode);

    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    void updateStatus(Long orderId, OrderStatus status);
}



