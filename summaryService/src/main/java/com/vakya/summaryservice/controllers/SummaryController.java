package com.vakya.summaryservice.controllers;

import com.vakya.summaryservice.dto.SummaryRequest;
import com.vakya.summaryservice.services.SummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateSummary(@RequestBody SummaryRequest request) {
        try {
            // Call the SummaryService to generate the summary based on the text from the file
            String summary = summaryService.generateSummary(request.getFilePath());
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating summary: " + e.getMessage());
        }
    }
}


