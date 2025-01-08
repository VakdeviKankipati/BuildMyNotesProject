package com.vakya.speechservice.services;

import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class SpeechService {

    @Value("${google.cloud.credentials.path}")
    private String googleCloudCredentialsPath;

    public String transcribeAudio(String filePath) throws IOException {
        System.out.println("Processing file at: " + filePath);

        // Set credentials for Google Cloud
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", googleCloudCredentialsPath);

        // Read the audio file
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        System.out.println("Audio file read successfully, size: " + data.length + " bytes.");

        try (SpeechClient speechClient = SpeechClient.create()) {
            // Configure the recognition settings
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.MP3)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("en-US")
                    .build();

            // Prepare the audio content for recognition
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(ByteString.copyFrom(data))
                    .build();

            // Make the transcription request
            RecognizeRequest request = RecognizeRequest.newBuilder()
                    .setConfig(config)
                    .setAudio(audio)
                    .build();

            // Get the transcription response from Google Cloud
            RecognizeResponse response = speechClient.recognize(request);
            System.out.println("Transcription response received: " + response);

            // Extract the transcribed text
            StringBuilder transcript = new StringBuilder();
            for (SpeechRecognitionResult result : response.getResultsList()) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                transcript.append(alternative.getTranscript()).append(" ");
            }

            return transcript.toString();  // Return the transcribed text
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during transcription: " + e.getMessage(); // Handle error gracefully
        }
    }

    // Method to save transcribed text to a file
    public void saveTranscriptionToFile(String transcribedText, String filePath) {
        try {
            // Create the file and write transcribed text to it
            File file = new File(filePath);
            // Ensure the file exists and write content
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(transcribedText);
            }
            System.out.println("Transcription saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error while saving transcription to file: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
