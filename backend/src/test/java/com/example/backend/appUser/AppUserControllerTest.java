package com.example.backend.appUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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

        this.mvc.perform(post("/api/app-user")
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

        this.mvc.perform(post("/api/app-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(response));

        this.mvc.perform(post("/api/app-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        MockMvcResultMatchers.status().isConflict());

    }


    @Test
    @WithMockUser(username = "Test User", password = "Test Password")
    void post_LoginWithRegisteredUser() throws Exception {

        /*
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(appUserRepository.findByUsername("Test User"))
                .thenReturn(
                        Optional.of(new AppUser(
                                "Test ID",
                                "Test User",
                                "Test Password",
                                "BASIC")));




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

         */

        this.mvc.perform(post("/api/app-user/login/"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                            """
                            {
                                "username": "Test User",
                                "password": "",
                                "role": "BASIC"
                            }
                            """));



    }

    @Test
    @WithMockUser(username = "Test User", password = "Test Password", roles = "BASIC")
    void getMe_LoggedInWithRegisteredUser() throws Exception {
        /*
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(appUserRepository.findByUsername("Test User"))
                .thenReturn(
                        Optional.of(new AppUser(
                                "Test ID",
                                "Test User",
                                "Test Password",
                                "BASIC")));



        String response = """
                    {
                    "username": "Test User",
                    "password": "",
                    "role": "BASIC"
                    }
                """;

         */

        this.mvc.perform(get("/api/app-user/me"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk());

       //                 MockMvcResultMatchers.content().json(response));

    }

    @Test
    void getMe_WhenNotLoggedInReturns401() throws Exception {
        this.mvc.perform(get("/api/app-user/me"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "Test User", roles = "BASIC")
    void logout_withRegisteredUser() throws Exception {
        mvc.perform(get("/api/app-user/logout"))
                .andExpect(status().isOk());
    }




}