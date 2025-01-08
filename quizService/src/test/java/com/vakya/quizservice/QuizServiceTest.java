package com.vakya.quizservice;

import com.vakya.quizservice.services.OpenAiService;
import com.vakya.quizservice.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceTest {

    @Mock
    private OpenAiService openAiService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateSummary_Success() throws Exception {
        String testFilePath = "test-summary.txt";
        String quizContent = "Sample Quiz Content";

        // Prepare a mock file
        File file = new File(testFilePath);
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Sample transcription content");
        }

        when(openAiService.generateQuiz("Sample transcription content")).thenReturn(quizContent);

        String result = quizService.generateSummary(testFilePath);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.contains("Quiz saved to file:"), "Result should indicate quiz was saved");
        verify(openAiService, times(1)).generateQuiz("Sample transcription content");

        // Clean up
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void testGenerateSummary_FileNotFound() {
        String nonexistentFilePath = "nonexistent-file.txt";

        Exception exception = assertThrows(IOException.class, () -> {
            quizService.generateSummary(nonexistentFilePath);
        });

        assertTrue(exception.getMessage().contains("File not found"), "Exception message should indicate file not found");
    }


}

