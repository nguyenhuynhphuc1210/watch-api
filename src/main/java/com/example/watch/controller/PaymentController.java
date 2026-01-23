package com.example.watch.controller;

import com.example.watch.dto.request.CheckoutRequestDTO;
import com.example.watch.dto.response.CheckoutResponseDTO;
import com.example.watch.entity.Order;
import com.example.watch.entity.Payment;
import com.example.watch.enums.OrderStatus;
import com.example.watch.enums.PaymentStatus;
import com.example.watch.repository.OrderRepository;
import com.example.watch.repository.PaymentRepository;
import com.example.watch.security.CustomUserDetails;
import com.example.watch.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final CheckoutService checkoutService;

    // 👇 thêm 2 repo này
    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;

    // ================= CHECKOUT =================
    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(
            @RequestBody CheckoutRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails cud) {

        return ResponseEntity.ok(
                checkoutService.checkout(dto, cud.getUser()));
    }

    // ================= VNPAY CALLBACK =================
    @GetMapping("/payment/vnpay-return")
    public ResponseEntity<?> vnpayReturn(
            @RequestParam Map<String, String> params) {

        String responseCode = params.get("vnp_ResponseCode");
        String orderCode = params.get("vnp_TxnRef");
        String transactionId = params.get("vnp_TransactionNo");

        Order order = orderRepo.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = paymentRepo.findByOrder_Id(order.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("00".equals(responseCode)) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setTransactionId(transactionId);
            payment.setPaidAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PAID);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            order.setStatus(OrderStatus.CANCELLED);
        }

        paymentRepo.save(payment);
        orderRepo.save(order);

        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8" />
                  <title>Thanh toán thành công</title>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <style>
                    body {
                      font-family: Arial, sans-serif;
                      text-align: center;
                      padding: 40px;
                    }
                    button {
                      margin-top: 20px;
                      padding: 14px 24px;
                      font-size: 16px;
                      border-radius: 10px;
                      border: none;
                      background: #C9A862;
                      color: white;
                    }
                  </style>
                </head>
                <body>
                  <h3>🎉 Thanh toán thành công</h3>
                  <p>Nhấn nút bên dưới để quay về ứng dụng</p>

                  <button onclick="openApp()">Mở ứng dụng</button>

                  <script>
                    function openApp() {
                      window.location.href = "exp://10.18.5.219:8081/--/payment-success?orderCode=%s";
                    }
                  </script>
                </body>
                </html>
                """.formatted(orderCode);

        return ResponseEntity.ok()
                .header("Content-Type", "text/html")
                .body(html);

    }
}
