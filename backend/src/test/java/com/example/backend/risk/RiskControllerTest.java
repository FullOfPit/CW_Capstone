package com.example.backend.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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




}