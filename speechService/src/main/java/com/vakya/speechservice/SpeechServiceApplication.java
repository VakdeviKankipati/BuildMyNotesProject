package com.vakya.speechservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class SpeechServiceApplication {

    public static void main(String[] args) {
        //System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "D:\\json\\speech-to-text-444409-52f86291d20c.json");
        SpringApplication.run(SpeechServiceApplication.class, args);


        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        File credentialsFile = new File(credentialsPath);

        if (credentialsFile.exists() && credentialsFile.canRead()) {
            System.out.println("Google Cloud credentials file is accessible: " + credentialsPath);
        } else {
            System.out.println("Google Cloud credentials file is NOT accessible: " + credentialsPath);
        }




    }

}
