package com.example.watch.repository;

import com.example.watch.entity.Order;
import com.example.watch.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ================= BASIC =================

    Optional<Order> findByOrderCode(String orderCode);

    List<Order> findByUser_Id(Long userId);

    List<Order> findByStatus(OrderStatus status);

    // ================= SECURITY =================

    Optional<Order> findByOrderCodeAndUser_Id(String orderCode, Long userId);

    boolean existsByIdAndUser_Id(Long orderId, Long userId);

    // ================= SORTING =================

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}
