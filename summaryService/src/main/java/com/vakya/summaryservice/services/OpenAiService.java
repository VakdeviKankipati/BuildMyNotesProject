package com.vakya.summaryservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {



    @Value("${openai.api.key}")
    private String openAiApiKey;

    //private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSummary(String text) {
        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4-0613");
        requestPayload.put("temperature", 0.7);

        // Defining the messages
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful assistant."));
        messages.add(Map.of("role", "user", "content", "Summarize the following text: " + text));

        requestPayload.put("messages", messages);

        // Construct the request entity with headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        // Make the API call and handle the response
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> choice = choices.get(0);

                // Extracting the 'content' from the 'message'
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                return (String) message.get("content");
            }
            throw new RuntimeException("Failed to parse OpenAI response");
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error from OpenAI API: " + e.getResponseBodyAsString(), e);
        }
       /* Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4-0613");
        requestPayload.put("temperature", 0.7);

        // Defining the messages
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful assistant."));
        messages.add(Map.of("role", "user", "content", "Summarize the following text: " + text));

        requestPayload.put("messages", messages);

        // Construct the request entity with headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        // Make the API call and handle the response
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> choice = choices.get(0);

                // Extracting the 'content' from the 'message'
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                return (String) message.get("content");
            }
            throw new RuntimeException("Failed to parse OpenAI response");
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error from OpenAI API: " + e.getResponseBodyAsString(), e);
        }*/

    }
}

