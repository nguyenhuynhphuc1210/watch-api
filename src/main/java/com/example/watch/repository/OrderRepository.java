package com.example.watch.repository;

import com.example.watch.entity.Order;
import com.example.watch.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ================= BASIC =================

    Optional<Order> findByOrderCode(String orderCode);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    // ================= SECURITY =================

    Optional<Order> findByOrderCodeAndUser_Id(String orderCode, Long userId);

    boolean existsByIdAndUser_Id(Long orderId, Long userId);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    @Query("""
            SELECT COUNT(o) > 0
            FROM Order o
            JOIN o.orderDetails d
            WHERE o.user.id = :userId
            AND o.status = :status
            AND d.product.id = :productId
            """)
    boolean hasCompletedOrderWithProduct(
            @Param("userId") Long userId,
            @Param("status") OrderStatus status,
            @Param("productId") Long productId);

}
