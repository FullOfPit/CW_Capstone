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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RiskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RiskRepository riskRepository;


    //                      -- Auxiliary
    private Risk generateTestRisk () {
        return new Risk(
                "Test ID",
                "Test Project ID",
                "Test Risk",
                "Test Description",
                List.of("Test Measure 1", "Test Measure 2"),
                1,
                1,
                1
        );
    }

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
    void getAll_returnsListWhenFilled () throws Exception {

        this.riskRepository.save(generateTestRisk());

        String expected = """
                [{
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Description",
                        "riskReductionMeasures": ["Test Measure 1", "Test Measure 2"],
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }]
                
                """;

        //When - Then
        mvc.perform(get("/api/risk"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    @Test
    @WithMockUser
    void create_returnsRiskCorrectly () throws Exception {
        //Given

        String request = """
                {
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Description",
                        "riskReductionMeasures": ["Test Measure 1", "Test Measure 2"],
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