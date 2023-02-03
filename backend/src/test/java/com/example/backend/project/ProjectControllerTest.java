package com.example.backend.project;

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

    String testProjectJson =
            """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": "0001-01-01",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "FINISHED",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
            """;

    @Test
    void getAllProjects_Returns401WhenNotLoggedIn() throws Exception {
        mvc.perform(get("/api/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAllProjects_NoProjectsRegistered_ReturnsEmptyListWhenLoggedIn() throws Exception {
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
    void getProjectByID_ProjectRegistered_ReturnsProject() throws Exception {
        //Implicitly tests that the createdAt date is automatically set to the current date

        mvc.perform(post("/api/projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testProjectJson))
                .andExpect(status().isOk());

        String response = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt":""" + LocalDate.now() + "," +  """
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "FINISHED",
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
    void getProjectByID_ProjectSaved_ChangesProjectStatusToFinishWhenFinishDateBeforeCurrentDate() throws Exception {

        String request = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": "0001-01-01",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "PLANNED",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
                """;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        String response = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt":""" + LocalDate.now() + "," +  """
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "FINISHED",
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
    void getProjectByID_ProjectSaved_ChangesProjectStatusToCurrentWhenFinishDateAfterCurrentDate() throws Exception {

        String request = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": "0001-01-01",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "9999-01-01",
                    "projectStatus": "PLANNED",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }
                """;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        String response = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt":""" + LocalDate.now() + "," +  """
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "9999-01-01",
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
    void createNewProject_ReturnsCorrectProject() throws Exception {
        //Implicitly tests that the createdAt date is automatically set to the current date
        //request == testProjectJSON

        String request = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "PLANNED",
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
                    "createdAt":""" + LocalDate.now() + "," +  """
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "PLANNED",
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
    void createNewProject_Returns401WhenNotLoggedIn() throws Exception {

        String request = testProjectJson;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void deleteProjectById_Returns405WhenProjectNotRegistered() throws Exception {

        mvc.perform(delete("/api/projects/testid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteProjectById_ListSizeReducesWhenProjectCorrectlyDeleted() throws Exception {

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testProjectJson))
                .andExpect(status().isOk());

        String response = """
                [{
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt":""" + LocalDate.now() + "," +  """
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "FINISHED",
                    "assessorName": "Test Assessor",
                    "projectDetails": "Test Details"
                }]
                """;

        mvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        mvc.perform(delete("/api/projects/testid"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser
    void updateProject_Returns405WhenProjectNotRegistered() throws Exception {

        String request = testProjectJson;

        mvc.perform(put("/api/projects/testid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void updateProject_correctlyUpdatesProjectAndReturnsChanges() throws Exception {
        //Alteration: projectDetails

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testProjectJson))
                .andExpect(status().isOk());

        String request = """
                {
                    "id": "testid",
                    "createdBy":"Test User",
                    "projectId": "Test Project ID",
                    "projectName": "Test Project Name",
                    "createdAt": "0001-01-01",
                    "plannedStartDate": "0001-01-01",
                    "plannedFinishDate": "0001-01-01",
                    "projectStatus": "FINISHED",
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

        String testUserOne = """
                    {
                        "id": "testuser1",
                        "username": "Test User 1",
                        "password": "Test Password 1",
                        "role": "BASIC"
                    }
                    """;

        mvc.perform(post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserOne))
                .andExpect(status().isOk());

        String testUserTwo = """
                    {
                        "id": "testuser2",
                        "username": "Test User 2",
                        "password": "Test Password 2",
                        "role": "BASIC"
                    }
                    """;

        mvc.perform(post("/api/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserTwo))
                .andExpect(status().isOk());

        String userOneTestProject = """
                    {
                        "id": "testidone",
                        "createdBy":"testuser1",
                        "projectId": "Test Project ID 1",
                        "projectName": "Test Project Name 1",
                        "createdAt": "0001-01-01",
                        "plannedStartDate": "0001-01-01",
                        "plannedFinishDate": "0001-01-01",
                        "projectStatus": "FINISHED",
                        "assessorName": "Test Assessor 1",
                        "projectDetails": "Test Details"
                    }
                    """;

        mvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userOneTestProject))
                .andExpect(status().isOk());

        String userTwoTestProject = """
                    {
                        "id": "testidtwo",
                        "createdBy":"testuser2",
                        "projectId": "Test Project ID 2",
                        "projectName": "Test Project Name 2",
                        "createdAt": "0001-01-01",
                        "plannedStartDate": "0001-01-01",
                        "plannedFinishDate": "0001-01-01",
                        "projectStatus": "FINISHED",
                        "assessorName": "Test Assessor 2",
                        "projectDetails": "Alternative Test Details"
                    }
                    """;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userTwoTestProject))
                .andExpect(status().isOk());


        String userTwoProjectString = """
                    [{
                        "id": "testidtwo",
                        "createdBy":"testuser2",
                        "projectId": "Test Project ID 2",
                        "projectName": "Test Project Name 2",
                        "createdAt":""" + LocalDate.now() + "," +  """
                        "plannedStartDate": "0001-01-01",
                        "plannedFinishDate": "0001-01-01",
                        "projectStatus": "FINISHED",
                        "assessorName": "Test Assessor 2",
                        "projectDetails": "Alternative Test Details"
                    }]
                    """;

        mvc.perform(get("/api/projects/app-users/testuser2"))
                .andExpect(status().isOk())
                .andExpect(content().json(userTwoProjectString));
    }
}