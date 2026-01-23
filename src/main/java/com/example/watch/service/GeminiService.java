package com.example.watch.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String askGemini(String prompt) {

        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent"
                + "?key=" + apiKey;

        System.out.println("====== GEMINI REQUEST ======");
        System.out.println("URL = " + url);
        System.out.println("PROMPT = \n" + prompt);
        System.out.println("============================");

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> res;

        try {
            res = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Không kết nối được Gemini API";
        }

        System.out.println("====== GEMINI RESPONSE ======");
        System.out.println(res.getBody());
        System.out.println("=============================");

        return extractText(res.getBody());
    }

    private String extractText(Map body) {
        try {
            List candidates = (List) body.get("candidates");
            Map content = (Map) ((Map) candidates.get(0)).get("content");
            List parts = (List) content.get("parts");
            return (String) ((Map) parts.get(0)).get("text");
        } catch (Exception e) {
            e.printStackTrace();
            return "Chatbot hiện không khả dụng.";
        }
    }
}
