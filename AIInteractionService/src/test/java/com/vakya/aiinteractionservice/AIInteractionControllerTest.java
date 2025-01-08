package com.vakya.aiinteractionservice;

import com.vakya.aiinteractionservice.controller.AIInteractionController;
import com.vakya.aiinteractionservice.service.AIInteractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AIInteractionControllerTest {

    private MockMvc mockMvc;
    private AIInteractionService aiInteractionService;

    @BeforeEach
    public void setup() {
        aiInteractionService = mock(AIInteractionService.class);
        AIInteractionController aiInteractionController = new AIInteractionController(aiInteractionService);
        mockMvc = MockMvcBuilders.standaloneSetup(aiInteractionController).build();
    }

}