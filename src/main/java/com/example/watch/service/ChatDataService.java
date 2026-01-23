package com.example.watch.service;

import com.example.watch.entity.Order;
import com.example.watch.entity.Product;
import com.example.watch.repository.OrderRepository;
import com.example.watch.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatDataService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public String buildContext(String question, Long userId) {

        StringBuilder context = new StringBuilder();

        // 🔹 SẢN PHẨM
        if (question.toLowerCase().contains("sản phẩm")) {
            List<Product> products = productRepo.findAll();

            context.append("Danh sách sản phẩm:\n");
            for (Product p : products) {
                context.append("- ")
                       .append(p.getName())
                       .append(", giá: ")
                       .append(p.getPrice())
                       .append(" VND\n");
            }
        }

        // 🔹 ĐƠN HÀNG
        if (question.toLowerCase().contains("đơn hàng")) {
            List<Order> orders = orderRepo.findByUserId(userId);

            context.append("Đơn hàng của khách:\n");
            for (Order o : orders) {
                context.append("- Mã: ")
                       .append(o.getOrderCode())
                       .append(", trạng thái: ")
                       .append(o.getStatus())
                       .append(", tổng: ")
                       .append(o.getTotalAmount())
                       .append("\n");
            }
        }

        return context.toString();
    }
}

