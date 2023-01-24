package com.example.backend.appUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AppUserControllerTest {

    @Autowired
    private MockMvc mvc;

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
    /*

    @Test
    @WithMockUser
    void post_LoginWithRegisteredUser() throws Exception {
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

        this.mvc.perform(post("/api/appuser/login")
                        .header("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

    }

    @Test
    @WithMockUser(username = "Test User", password = "Test Password", roles = "BASIC")
    void get_LoggedInWithRegisteredUser() throws Exception {
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

        this.mvc.perform(get("/api/appuser/me"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

    }

     */


}