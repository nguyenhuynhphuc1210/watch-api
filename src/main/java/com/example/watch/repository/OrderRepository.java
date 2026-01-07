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

    // ================= SECURITY / BUSINESS =================

    // Lấy order theo code + user (tránh user xem order người khác)
    Optional<Order> findByOrderCodeAndUser_Id(String orderCode, Long userId);

    // Kiểm tra order thuộc user hay không
    boolean existsByIdAndUser_Id(Long orderId, Long userId);

    // ================= SORTING =================

    // Lịch sử mua hàng (mới nhất trước)
    List<Order> findByUser_IdOrderByCreatedAtDesc(Long userId);

    // Admin xem order theo trạng thái (mới nhất trước)
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}
