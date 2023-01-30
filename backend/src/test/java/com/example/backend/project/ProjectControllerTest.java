package com.example.backend.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectRepository projectRepository;

    Project generateTestProject = new Project(
            "testid",
            "Test User",
            "Test Project ID",
            "Test Project Name",
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            ProjectStatus.CURRENT,
            "Test Assessor",
            "Test Details");

    @Test
    void getAll_Returns401WhenNotLoggedIn() throws Exception {
        mvc.perform(get("/api/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAll_ReturnsEmptyListWhenLoggedIn_NoProjectsRegistered() throws Exception {
        mvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getByID_Returns401WhenNotLoggedIn() throws Exception {
        mvc.perform(get("/api/projects/testid"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getByID_ReturnsProjectWhenIDRegistered() throws Exception {

        this.projectRepository.save(generateTestProject);

        String response = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": "0001-01-01",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "CURRENT",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
                """;

        mvc.perform(get("/api/projects/testid"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    @WithMockUser
    void getById_ReturnsExceptionWhenIdNotRegistered() throws Exception {
        mvc.perform(get("/api/projects/testid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void create_ReturnsCorrectProject() {

    }

}