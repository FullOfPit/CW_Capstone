package com.example.backend.appuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AppUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AppUserRepository appUserRepository;
    


    @Test
    void create_returnsCorrectUserData() throws Exception {

        String request = """
                {
                    "username": "Test User",
                    "password": "Test Password"
                }
                """;

        String response = """
                {
                    "username": "Test User",
                    "password": "",
                    "role": "BASIC"
                }
                """;

        this.mvc.perform(post("/api/appuser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void create_throws409WhenAttemptAtCreatingRegisteredUsername() throws Exception {

        String request = """
                {
                    "username": "Test User",
                    "password": "Test Password"
                }
                """;
        String response = """
                   {
                    "username": "Test User",
                    "password": "",
                    "role": "BASIC"
                    }
                """;

        this.mvc.perform(post("/api/appuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(response));

        this.mvc.perform(post("/api/appuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        MockMvcResultMatchers.status().isConflict());

    }


    @Test
    @WithMockUser(username = "Test User", roles = "BASIC")
    void post_LoginWithRegisteredUser() throws Exception {

        String request = """
                {
                    "username": "Test User",
                    "password": "Test Password"
                }
                """;

        this.mvc.perform(post("/api/appuser/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Test User", password = "Test Password", roles = "BASIC")
    void getMe_LoggedInWithRegisteredUser() throws Exception {

        appUserRepository.save(new AppUser("Test ID", "Test User", "Test Password", "BASIC"));

        String response = """
                    {
                    "username": "Test User",
                    "password": "",
                    "role": "BASIC"
                    }
                """;



        this.mvc.perform(get("/api/appuser/me"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(response));

    }

    @Test
    void getMe_WhenNotLoggedInReturns401() throws Exception {
        this.mvc.perform(get("/api/appuser/me"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "Test User", roles = "BASIC")
    void logout_withRegisteredUser() throws Exception {
        mvc.perform(get("/api/appuser/logout"))
                .andExpect(status().isOk());
    }




}