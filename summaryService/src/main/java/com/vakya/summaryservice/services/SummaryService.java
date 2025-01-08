package com.vakya.summaryservice.services;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class SummaryService {

    private final OpenAiService openAiService;

    public SummaryService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public String generateSummary(String filePath) throws IOException {
        // Read the transcription text file
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        // Read the content of the file
        String text = Files.readString(file.toPath());
        System.out.println("Read transcription: " + text);

        // Call OpenAI service to generate a summary of the transcription text
        String summary = openAiService.getSummary(text);

        // Step 3: Save the summary to a new file
        String summaryFilePath = "C:/Users/Surya/AppData/Local/Temp/summary.txt";
        saveSummaryToFile(summary, summaryFilePath);

        return "Summary saved to file: " + summaryFilePath;
    }
    private void saveSummaryToFile(String summary, String summaryFilePath) throws IOException {
        File summaryFile = new File(summaryFilePath);
        // Create the file if it doesn't exist
        if (!summaryFile.exists()) {
            summaryFile.createNewFile();
        }

        // Write the summary content to the file
        Files.write(summaryFile.toPath(), summary.getBytes());
    }


}


