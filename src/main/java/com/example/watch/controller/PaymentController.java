package com.example.watch.controller;

import com.example.watch.dto.request.CheckoutRequestDTO;
import com.example.watch.dto.response.CheckoutResponseDTO;
import com.example.watch.security.CustomUserDetails;
import com.example.watch.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(
            @RequestBody CheckoutRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails cud) {
        return ResponseEntity.ok(
                checkoutService.checkout(dto, cud.getUser()));
    }
}
