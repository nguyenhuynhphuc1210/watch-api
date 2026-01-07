package com.example.watch.mapper;

import com.example.watch.dto.response.OrderDetailResponseDTO;
import com.example.watch.entity.Order;
import com.example.watch.entity.OrderDetail;
import com.example.watch.entity.Product;

import java.math.BigDecimal;

public class OrderDetailMapper {

    /**
     * Dùng khi tạo order (checkout)
     */
    public static OrderDetail toEntity(
            Order order,
            Product product,
            Integer quantity
    ) {
        OrderDetail detail = new OrderDetail();

        detail.setOrder(order);
        detail.setProduct(product);
        detail.setPrice(product.getPrice());
        detail.setQuantity(quantity);

        BigDecimal total = product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        detail.setTotal(total);

        return detail;
    }

    /**
     * Dùng khi trả response cho client
     */
    public static OrderDetailResponseDTO toDTO(OrderDetail detail) {
        return new OrderDetailResponseDTO(
                detail.getProduct().getId(),
                detail.getProduct().getName(),
                detail.getPrice(),
                detail.getQuantity(),
                detail.getTotal()
        );
    }
}
