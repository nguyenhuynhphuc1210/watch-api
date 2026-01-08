package com.example.watch.service.impl;

import com.example.watch.dto.request.OrderRequestDTO;
import com.example.watch.dto.response.OrderResponseDTO;
import com.example.watch.entity.CartItem;
import com.example.watch.entity.Order;
import com.example.watch.entity.OrderDetail;
import com.example.watch.entity.User;
import com.example.watch.enums.OrderStatus;
import com.example.watch.mapper.OrderDetailMapper;
import com.example.watch.mapper.OrderMapper;
import com.example.watch.repository.CartItemRepository;
import com.example.watch.repository.OrderDetailRepository;
import com.example.watch.repository.OrderRepository;
import com.example.watch.repository.UserRepository;
import com.example.watch.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    // ================= CREATE ORDER (CHECKOUT) =================
    @Override
    public OrderResponseDTO createOrder(Long userId, OrderRequestDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ LẤY CART THEO userId (KHÔNG dùng User_Id)
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 1️⃣ Create Order
        Order order = OrderMapper.toEntity(dto, user);
        Order savedOrder = orderRepository.save(order);

        // 2️⃣ Create OrderDetails từ CartItem
        List<OrderDetail> orderDetails = cartItems.stream()
                .map(item -> OrderDetailMapper.toEntity(
                        savedOrder,
                        item.getProduct(),
                        item.getQuantity()
                ))
                .toList();

        orderDetailRepository.saveAll(orderDetails);

        // 3️⃣ Calculate total amount
        BigDecimal totalAmount = orderDetails.stream()
                .map(od -> od.getPrice()
                        .multiply(BigDecimal.valueOf(od.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        savedOrder.setTotalAmount(totalAmount);
        // ❌ không cần save vì @Transactional

        // 4️⃣ Clear cart
        cartItemRepository.deleteByUserId(userId);

        return OrderMapper.toDTO(savedOrder, orderDetails);
    }

    // ================= GET ORDERS BY USER =================
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {

        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(order -> OrderMapper.toDTO(
                        order,
                        orderDetailRepository.findByOrderId(order.getId())
                ))
                .toList();
    }

    // ================= GET ORDER BY CODE (USER) =================
    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getByOrderCode(String orderCode) {

        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderMapper.toDTO(
                order,
                orderDetailRepository.findByOrderId(order.getId())
        );
    }

    // ================= GET ORDERS BY STATUS (ADMIN) =================
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {

        return orderRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(order -> OrderMapper.toDTO(
                        order,
                        orderDetailRepository.findByOrderId(order.getId())
                ))
                .toList();
    }

    // ================= UPDATE ORDER STATUS (ADMIN) =================
    @Override
    public void updateStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        // @Transactional → tự flush
    }
}

