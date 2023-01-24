package com.example.backend.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class RiskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAll_returns401WhenNotLoggedIn () throws Exception {
        mvc.perform(get("/api/risk"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAll_returnsEmptyWhenNoRisksRegistered () throws Exception {
        mvc.perform(get("/api/risk"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    @WithMockUser
    void create_returnsRiskCorrectly () throws Exception {
        //Given

        String request = """
                {
                        "projectId": "Test Project",
                        "riskName": "Test Risk",
                        "riskDescription": "Test description",
                        "riskReductionMeasures": ["measure 1" ,"measure 2"],
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }
               
                """;

        //When - Then

        mvc.perform(post("/api/risk")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(request));

    }
    
}