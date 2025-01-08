package com.vakya.quizservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenAiService {
    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Generate a quiz based on the provided text
    public String generateQuiz(String text) {
        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4-0613");
        requestPayload.put("temperature", 0.7);

        // Prepare the messages
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful assistant."));
        messages.add(Map.of("role", "user", "content", "Create a quiz with multiple choice questions based on the following text: " + text));

        requestPayload.put("messages", messages);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        // Make the API call to OpenAI and get the quiz
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                return (String) message.get("content");
            }
            throw new RuntimeException("Failed to parse OpenAI response");
        } catch (Exception e) {
            throw new RuntimeException("Error from OpenAI API: " + e.getMessage(), e);
        }
    }
}
