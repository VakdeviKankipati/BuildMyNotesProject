package com.vakya.quizservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;


@Service
public class QuizService {

    private final OpenAiService openAiService;
    private final RestTemplate restTemplate;

    @Value("${summary.service.url}")
    private String summaryServiceUrl;

    public QuizService(OpenAiService openAiService, RestTemplate restTemplate) {
        this.openAiService = openAiService;
        this.restTemplate = restTemplate;
    }

    // Method to fetch the summary from the SummaryService and generate a quiz
    public String generateSummary(String summaryFilePath) throws IOException {

        File file = new File(summaryFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + summaryFilePath);
        }

        // Read the content of the file
        String text = Files.readString(file.toPath());
        System.out.println("Read transcription: " + text);


        // Generate a quiz using OpenAI
        String quiz = openAiService.generateQuiz(text);

        // Save the quiz to a file
        String quizFilePath = "C:/Users/Surya/AppData/Local/Temp/quiz.txt";  // Customize the quiz file name if needed
        saveQuizToFile(quiz, quizFilePath);

        return "Quiz saved to file: " + quizFilePath;
    }

    /*// Fetch the summary from SummaryService
    private String fetchSummaryFromService(String summaryFilePath) {
        String url = summaryServiceUrl + "/api/summary/generate?filePath=" + summaryFilePath;

        // Make HTTP request to SummaryService
        return restTemplate.getForObject(url, String.class);
    }*/

    // Save the quiz to a file
    private void saveQuizToFile(String quiz, String quizFilePath) throws IOException {
        File quizFile = new File(quizFilePath);
        if (!quizFile.exists()) {
            quizFile.createNewFile();
        }

        // Write the quiz content to the file
        Files.write(quizFile.toPath(), quiz.getBytes());
    }



}
