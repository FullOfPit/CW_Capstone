package com.example.backend.risk;
import com.example.backend.project.Project;
import com.example.backend.project.ProjectRepository;
import com.example.backend.project.ProjectStatus;
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

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RiskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    //                      -- Auxiliary
    Risk testRisk = new Risk(
                "testid",
                "Test Project ID",
                "Test Risk",
                "Test Description",
                "Test Measure 1",
                1,
                1,
                1);

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

        this.riskRepository.save(testRisk);

        String expected = """
                [{
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Description",
                        "riskReductionMeasures": "Test Measure 1",
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

        String response = """
                [{
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Description",
                        "riskReductionMeasures": "Test Measure 1",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }]
               
                """;
        this.riskRepository.save(testRisk);

        mvc.perform(get("/api/risks"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        mvc.perform(delete("/api/risks/testid"))
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
        this.riskRepository.save(testRisk);

        String request = """
                {
                        "projectId": "Test Project ID",
                        "riskName": "Test Risk",
                        "riskDescription": "Alternative Test Description",
                        "riskReductionMeasures": "Test Measure 1",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }
               
                """;

        mvc.perform(put("/api/risks/testid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(request));
    }

    @Test
    @WithMockUser
    void getAllByProjectId_ReturnsCorrectListOfRisksWhenCorrectUser() throws Exception {

        Project projectOne = new Project(
                "testprojectid1",
                "Test User",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Test Details");

        Project projectTwo = new Project(
                "testprojectid2",
                "Test User",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                LocalDate.of(1,1,1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Altered Test Details");

        this.projectRepository.save(projectOne);
        this.projectRepository.save(projectTwo);

        Risk riskProjectOne = new Risk(
                "testid1",
                "testprojectid1",
                "Test Risk",
                "Test Risk Description",
                "Test Risk Reduction Measures",
                1,
                1,
                1
        );

        Risk riskProjectTwo = new Risk(
                "testid2",
                "testprojectid2",
                "Test Risk",
                "Test Risk Description",
                "Test Risk Reduction Measures",
                1,
                1,
                1
        );

        String response = """
                [{
                        "projectId": "testprojectid2",
                        "riskName": "Test Risk",
                        "riskDescription": "Test Risk Description",
                        "riskReductionMeasures": "Test Risk Reduction Measures",
                        "healthHazard": 1,
                        "probability": 1,
                        "frequency": 1
                    }]
               
                """;

        this.riskRepository.save(riskProjectOne);
        this.riskRepository.save(riskProjectTwo);

        mvc.perform(get("/api/risks/projects/testprojectid2"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

}