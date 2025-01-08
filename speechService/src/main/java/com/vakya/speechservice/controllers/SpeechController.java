package com.vakya.speechservice.controllers;

import com.vakya.speechservice.services.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api/speech")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @PostMapping("/transcribe")
    public String transcribeAudio(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file with an absolute path
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir")); // Use the system temp directory
            Path tempFilePath = tempDir.resolve("temp_audio_file_" + System.currentTimeMillis() + ".mp3");
            System.out.println("Saving file to: " + tempFilePath.toAbsolutePath());
            file.transferTo(tempFilePath.toFile());
            System.out.println("File saved successfully at: " + tempFilePath);

            // Call the transcription service
            String transcription = speechService.transcribeAudio(tempFilePath.toString());

            // Save the transcription to a file (separate from the audio file)
            String transcriptionFilePath = "C:/Users/Surya/AppData/Local/Temp/transcription.txt"; // Specify path for transcription file
            speechService.saveTranscriptionToFile(transcription, transcriptionFilePath);
            System.out.println("Transcribed text saved to: " + transcriptionFilePath);

            // Clean up temporary audio file after transcription is done
            Files.delete(tempFilePath);
            System.out.println("Temporary audio file deleted.");

            return transcription;  // Return the transcribed text to the user
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing the audio file: " + e.getMessage();
        }
    }





}

