package com.example.watch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.watch.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrder_Id(Long orderId);
}

