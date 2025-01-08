package com.vakya.aiinteractionservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIInteractionService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate;

    public AIInteractionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAnswerFromAI(String question) {
        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4");
        requestPayload.put("temperature", 0.7);

        // Constructing the messages for the chat prompt
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are an expert teacher. Answer the following question.");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", question);

        requestPayload.put("messages", new Object[]{systemMessage, userMessage});

        // Adding headers for authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        try {
            // Make the POST request to OpenAI API
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            // Extracting the generated answer from the response
            if (responseBody != null && responseBody.containsKey("choices")) {
                Map<String, Object> choice = (Map<String, Object>) ((List) responseBody.get("choices")).get(0);
                Map<String, String> message = (Map<String, String>) choice.get("message");
                return message.get("content");
            }

            return "Sorry, I couldn't understand your question.";

        } catch (Exception e) {
            return "There was an error processing your request.";
        }
    }
}

