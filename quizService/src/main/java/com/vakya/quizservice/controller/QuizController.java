package com.vakya.quizservice.controller;

import com.vakya.quizservice.dto.QuizRequest;
import com.vakya.quizservice.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/quizzes")
public class QuizController {

    private QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService=quizService;
    }


    @PostMapping("/generate")
    public ResponseEntity<String> generateQuiz(@RequestBody QuizRequest request) {
        try {
            // Call the SummaryService to generate the summary based on the text from the file
            String summary = quizService.generateSummary(request.getFilePath());
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating summary: " + e.getMessage());
        }
    }
}

