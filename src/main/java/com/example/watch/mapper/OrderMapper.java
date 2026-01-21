package com.example.watch.mapper;

import com.example.watch.dto.request.OrderRequestDTO;
import com.example.watch.dto.response.OrderDetailResponseDTO;
import com.example.watch.dto.response.OrderResponseDTO;
import com.example.watch.entity.Order;
import com.example.watch.entity.User;
import com.example.watch.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderMapper {

    /**
     * DTO → Entity (CREATE ORDER)
     */
    public static Order toEntity(OrderRequestDTO dto, User user) {

        Order order = new Order();
        order.setUser(user);
        order.setOrderCode("ORD-" + UUID.randomUUID());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(dto.getShippingAddress());
        order.setNote(dto.getNote());

        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO); // tạm, sẽ update sau

        return order;
    }

    /**
     * Entity → DTO
     */
    public static OrderResponseDTO toDTO(Order order) {

        List<OrderDetailResponseDTO> detailDTOs =
                order.getOrderDetails().stream()
                        .map(OrderDetailMapper::toDTO)
                        .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderCode(),
                order.getStatus(),

                order.getShippingAddress(),
                order.getNote(),

                order.getTotalAmount(), // LẤY TỪ ENTITY
                detailDTOs,

                order.getCreatedAt()
        );
    }
}
