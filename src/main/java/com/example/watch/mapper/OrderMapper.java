package com.example.watch.mapper;

import com.example.watch.dto.request.OrderRequestDTO;
import com.example.watch.dto.response.OrderDetailResponseDTO;
import com.example.watch.dto.response.OrderResponseDTO;
import com.example.watch.entity.Order;
import com.example.watch.entity.OrderDetail;
import com.example.watch.entity.User;
import com.example.watch.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderMapper {

    /**
     * DTO → Entity (CREATE ORDER)
     */
    public static Order toEntity(OrderRequestDTO dto, User user) {

        Order order = new Order();
        order.setUser(user);
        order.setOrderCode(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(dto.getShippingAddress());
        order.setNote(dto.getNote());

        return order;
    }

    /**
     * Entity → DTO
     */
    public static OrderResponseDTO toDTO(
            Order order,
            List<OrderDetail> orderDetails
    ) {

        List<OrderDetailResponseDTO> detailDTOs = orderDetails.stream()
                .map(OrderDetailMapper::toDTO)
                .toList();

        BigDecimal totalAmount = detailDTOs.stream()
                .map(OrderDetailResponseDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderCode(),
                order.getStatus(),

                order.getShippingAddress(),
                order.getNote(),

                totalAmount,
                detailDTOs,

                order.getCreatedAt()
        );
    }
}
