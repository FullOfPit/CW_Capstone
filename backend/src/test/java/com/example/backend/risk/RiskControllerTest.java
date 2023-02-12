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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RiskControllerTest {

    @Autowired
    private MockMvc mvc;

    //                      -- Auxiliary

    String testRiskJson = """
            {
                "id": "testriskid",
                "projectId": "testprojectid",
                "riskName": "Test Risk Name",
                "riskDescription": "Test Risk Description",
                "riskReductionMeasures": "Test Risk Reduction Measure",
                "healthHazard": 1,
                "probability": 1,
                "frequency": 1
            }
            """;

    @Test
    void getAll_returns401WhenNotLoggedIn () throws Exception {
        mvc.perform(get("/api/risks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAll_returnsEmptyWhenNoRisksRegistered () throws Exception {
        mvc.perform(get("/api/risks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    @WithMockUser
    void getAll_returnsListWhenFilled () throws Exception {

        mvc.perform(post("/api/risks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRiskJson))
                .andExpect(status().isOk());

        String expected = """
                [{
                        "projectId": "testprojectid",
                        "riskName": "Test Risk Name",
                        "riskDescription": "Test Risk Description",
                        "riskReductionMeasures": "Test Risk Reduction Measure",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }]
                
                """;

        //When - Then
        mvc.perform(get("/api/risks"))
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
                        "riskReductionMeasures": "Test Measure 1",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }
             
                """;

        //When - Then

        mvc.perform(post("/api/risks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(request));
    }

    @Test
    void create_Returns401WhenNotLoggedIn() throws Exception {

        String request = """
                {
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Description",
                        "riskReductionMeasures": "Test Measure 1",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }
               
                """;

        mvc.perform(post("/api/risks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void deleteById_Returns405WhenRiskNotRegistered() throws Exception{
        mvc.perform(delete("/api/risks/testid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteById_ListSizeReducesWhenRiskCorrectlyDeleted() throws Exception {

        mvc.perform(post("/api/risks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRiskJson))
                .andExpect(status().isOk());

        String response = """
                [{
                        "projectId": "testprojectid",
                        "riskName": "Test Risk Name",
                        "riskDescription": "Test Risk Description",
                        "riskReductionMeasures": "Test Risk Reduction Measure",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }]
               
                """;

        mvc.perform(get("/api/risks"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        mvc.perform(delete("/api/risks/testriskid"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/risks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser
    void update_Returns405WhenRiskNotRegistered() throws Exception {

        String request = """
                {
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Description",
                        "riskReductionMeasures": "Test Measure 1",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }
               
                """;
        mvc.perform(put("/api/risks/testid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void update_CorrectlyUpdatesRiskAndReturnsChanges() throws Exception {

        mvc.perform(post("/api/risks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRiskJson))
                .andExpect(status().isOk());

        String request = """
                {
                        "projectId": "testriskid",
                        "riskName": "Test Risk",
                        "riskDescription": "Alternative Test Description",
                        "riskReductionMeasures": "Test Measure 1",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }
               
                """;

        mvc.perform(put("/api/risks/testriskid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(request));
    }

    @Test
    @WithMockUser
    void getAllByProjectId_ReturnsCorrectListOfRisksWhenCorrectUser() throws Exception {

        String testProjectOne = """
                    {
                        "id": "testprojectidone",
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
                        .content(testProjectOne))
                .andExpect(status().isOk());

        String testProjectTwo = """
                    {
                        "id": "testprojectidtwo",
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
                        .content(testProjectTwo))
                .andExpect(status().isOk());

        String testRiskOne = """
            {
                "id": "testriskid",
                "projectId": "testprojectidone",
                "riskName": "Test Risk Name",
                "riskDescription": "Test Risk Description",
                "riskReductionMeasures": "Test Risk Reduction Measure",
                "healthHazard": 1,
                "probability": 1,
                "frequency": 1
            }
            """;

        mvc.perform(post("/api/risks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRiskOne))
                .andExpect(status().isOk());

        String testRiskTwo = """
            {
                "id": "testriskid",
                "projectId": "testprojectidtwo",
                "riskName": "Test Risk Name",
                "riskDescription": "Alternative Test Risk Description",
                "riskReductionMeasures": "Test Risk Reduction Measure",
                "healthHazard": 1,
                "probability": 1,
                "frequency": 1
            }
            """;

        mvc.perform(post("/api/risks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRiskTwo))
                .andExpect(status().isOk());

        String response = """
                [{
                        "projectId": "testprojectidtwo",
                        "riskName": "Test Risk Name",
                        "riskDescription": "Alternative Test Risk Description",
                        "riskReductionMeasures": "Test Risk Reduction Measure",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }]
               
                """;

        mvc.perform(get("/api/risks/projects/testprojectidtwo"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}