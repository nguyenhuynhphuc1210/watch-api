package com.example.watch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import com.example.watch.security.CustomUserDetails;
import com.example.watch.service.ChatDataService;
import com.example.watch.service.GeminiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final GeminiService geminiService;
    private final ChatDataService chatDataService;

    @PostMapping
    public ResponseEntity<?> chat(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal CustomUserDetails cud
    ) {
        // 🔐 check login
        if (cud == null || cud.getUser() == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("reply", "Vui lòng đăng nhập để sử dụng chatbot."));
        }

        String question = body.get("message");

        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("reply", "Câu hỏi không hợp lệ."));
        }

        Long userId = cud.getUser().getId();

        // 🔹 build dữ liệu từ DB
        String context = chatDataService.buildContext(question, userId);

        String prompt = """
        Bạn là chatbot bán đồng hồ của cửa hàng WATCH.

        QUY TẮC:
        - CHỈ sử dụng dữ liệu bên dưới để trả lời
        - Nếu không có thông tin → nói "Hiện chưa có thông tin phù hợp"
        - KHÔNG bịa, KHÔNG suy đoán

        DỮ LIỆU:
        %s

        CÂU HỎI KHÁCH HÀNG:
        %s
        """.formatted(context, question);

        String answer = geminiService.askGemini(prompt);

        return ResponseEntity.ok(Map.of("reply", answer));
    }
}


