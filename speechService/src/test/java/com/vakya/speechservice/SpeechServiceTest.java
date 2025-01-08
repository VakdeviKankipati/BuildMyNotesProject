package com.vakya.speechservice;

import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;
import com.vakya.speechservice.services.SpeechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpeechServiceTest {

    @InjectMocks
    private SpeechService speechService;

    @Mock
    private SpeechClient mockSpeechClient;

    @Value("${google.cloud.credentials.path}")
    private String googleCloudCredentialsPath;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testSaveTranscriptionToFile_Success() {
        // Arrange
        String transcribedText = "This is a test transcription.";
        String filePath = "test_transcription.txt";

        // Act
        speechService.saveTranscriptionToFile(transcribedText, filePath);

        // Assert
        File file = new File(filePath);
        assertTrue(file.exists(), "Transcription file should exist");

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            assertEquals(transcribedText, content, "File content should match transcribed text");
        } catch (Exception e) {
            fail("Failed to read transcription file");
        } finally {
            // Clean-up
            file.delete();
        }
    }
}

