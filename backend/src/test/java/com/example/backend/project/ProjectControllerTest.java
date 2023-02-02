package com.example.backend.project;

import com.example.backend.appuser.AppUser;
import com.example.backend.appuser.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @Autowired
    private AppUserRepository appUserRepository;

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
    void create_ReturnsCorrectProject() throws Exception {

        String request = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "CURRENT",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
                """;

        String response = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": """ + LocalDate.now() + "," +  """
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "CURRENT",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
                """;

        mvc.perform(post("/api/projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void create_Returns401WhenNotLoggedIn() throws Exception {

        String request = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "CURRENT",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
                """;
        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void deleteById_Returns405WhenProjectNotRegistered() throws Exception {
        mvc.perform(delete("/api/projects/testid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteById_ListSizeReducesWhenProjectCorrectlyDeleted() throws Exception {

        String response = """
                [{
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
                }]
                """;

        this.projectRepository.save(generateTestProject);

        mvc.perform(get("/api/projects")).andExpect(status().isOk()).andExpect(content().json(response));


        mvc.perform(delete("/api/projects/testid"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser
    void update_Returns405WhenProjectNotRegistered() throws Exception {

        String request = """
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
        mvc.perform(put("/api/projects/testid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void update_correctlyUpdatesProjectAndReturnsChanges() throws Exception {
        this.projectRepository.save(generateTestProject);

        String request = """
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
                    "projectDetails": "Altered Test Details"
                }
                """;

        mvc.perform(put("/api/projects/testid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(request));
    }

    @Test
    @WithMockUser(username = "Test User 2")
    void getAllByUserId_ReturnsCorrectListOfProjectsWhenCorrectUser() throws Exception {

        this.appUserRepository.save(
                new AppUser(
                        "testuser1",
                        "Test User 1",
                        "Test Password 1",
                        "BASIC"));
        this.appUserRepository.save(
                new AppUser(
                        "testuser2",
                        "Test User 2",
                        "Test Password 2",
                        "BASIC"));

        Project userOneProject = new Project(
                "testid1",
                "testuser1",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Test Details");

        Project userTwoProject = new Project(
                "testid2",
                "testuser2",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Altered Test Details");

        this.projectRepository.save(userOneProject);
        this.projectRepository.save(userTwoProject);

        String userTwoProjectString = """
                [{
                    "id": "testid2",
                    "createdBy":"testuser2",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": "0001-01-01",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "CURRENT",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Altered Test Details"
                }]
                """;

        mvc.perform(get("/api/projects/app-users/testuser2"))
                .andExpect(status().isOk())
                .andExpect(content().json(userTwoProjectString));
    }
}