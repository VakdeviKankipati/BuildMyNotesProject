package com.vakya.userrservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpeechServiceClient {
    private final RestTemplate restTemplate;

    @Autowired
    public SpeechServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyLogin(String userId, String username) {
        String speechServiceUrl = "http://localhost:8081/api/speech/on-login";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userId", userId);
        requestBody.put("username", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(speechServiceUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("SpeechService response: " + response.getBody());
        } else {
            System.err.println("Error communicating with SpeechService: " + response.getStatusCode());
        }
    }
}
