package com.vakya.aiinteractionservice.controller;


import com.vakya.aiinteractionservice.service.AIInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai-interaction")
public class AIInteractionController {

    private final AIInteractionService aiInteractionService;

    @Autowired
    public AIInteractionController(AIInteractionService aiInteractionService) {
        this.aiInteractionService = aiInteractionService;
    }

    // Endpoint to ask a question to the AI-powered expert
    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {
        return aiInteractionService.getAnswerFromAI(question);
    }
}

