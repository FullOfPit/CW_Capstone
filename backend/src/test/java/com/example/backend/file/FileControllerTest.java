package com.example.backend.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    MockMultipartFile testMultipartFile =
            new MockMultipartFile(
                "file",
                "testfilename.pdf",
                "multipart/form-data",
                "Test Content".getBytes()
            );

    String testProjectJSON = """
            {
                "id": "testprojectid",
                "createdBy": "testuserid",
                "projectId": "Test Project ID",
                "projectName": "Test Project Name",
                "createdAt": "0001-01-01",
                "plannedStartDate": "0001-01-01",
                "plannedFinishDate": "9999-01-01",
                "projectStatus": "CURRENT",
                "assessorName": "Test Assessor",
                "projectDetails": "Test Details",
                "documentIds": ["documentidone"]
            }
            """;

    @Test
    void getFileByID_Returns401WhenNotLoggedIn() throws Exception {

        mvc.perform(get("/api/files/testfileid"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser
    void uploadFile_WithExistingProjectId_WhenLoggedIn_ReturnsContent() throws Exception {

        String response = """
            {
                "id": "testprojectid",
                "createdBy": "testuserid",
                "projectId": "Test Project ID",
                "projectName": "Test Project Name",
                "createdAt":""" + LocalDate.now() + "," +  """
                "plannedStartDate": "0001-01-01",
                "plannedFinishDate": "9999-01-01",
                "projectStatus": "CURRENT",
                "assessorName": "Test Assessor",
                "projectDetails": "Test Details",
                "documentIds": ["documentidone"]
            }
            """;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testProjectJSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        String fileResponse = mvc.perform(multipart("/api/files/testprojectid")
                        .file(testMultipartFile))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String fileId = new ObjectMapper().readValue(fileResponse, HashMap.class).get("id").toString();

        mvc.perform(get("/api/files/" + fileId))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Content"));
    }

    @Test
    @WithMockUser
    void deleteFile_WithExistingProjectId_WhenLoggedIn() throws Exception {

        String response = """
            {
                "id": "testprojectid",
                "createdBy": "testuserid",
                "projectId": "Test Project ID",
                "projectName": "Test Project Name",
                "createdAt":""" + LocalDate.now() + "," +  """
                "plannedStartDate": "0001-01-01",
                "plannedFinishDate": "9999-01-01",
                "projectStatus": "CURRENT",
                "assessorName": "Test Assessor",
                "projectDetails": "Test Details",
                "documentIds": ["documentidone"]
            }
            """;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testProjectJSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        String fileResponse = mvc.perform(multipart("/api/files/testprojectid")
                        .file(testMultipartFile))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String fileId = new ObjectMapper().readValue(fileResponse, HashMap.class).get("id").toString();

        mvc.perform(delete("/api/files/" + fileId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getFileMetadata_WithExistingProjectId_WhenLoggedIn() throws Exception {

        String request = """
            {
                "id": "testprojectid",
                "createdBy": "testuserid",
                "projectId": "Test Project ID",
                "projectName": "Test Project Name",
                "createdAt": "0001-01-01",
                "plannedStartDate": "0001-01-01",
                "plannedFinishDate": "9999-01-01",
                "projectStatus": "CURRENT",
                "assessorName": "Test Assessor",
                "projectDetails": "Test Details",
                "documentIds": []
            }
            """;

        String response = """
            {
                "id": "testprojectid",
                "createdBy": "testuserid",
                "projectId": "Test Project ID",
                "projectName": "Test Project Name",
                "createdAt":""" + LocalDate.now() + "," +  """
                "plannedStartDate": "0001-01-01",
                "plannedFinishDate": "9999-01-01",
                "projectStatus": "CURRENT",
                "assessorName": "Test Assessor",
                "projectDetails": "Test Details",
                "documentIds": []
            }
            """;

        mvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(response));


        String fileResponse = mvc.perform(multipart("/api/files/testprojectid")
                        .file(testMultipartFile))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String fileId = new ObjectMapper().readValue(fileResponse, HashMap.class).get("id").toString();

        String projectUpdate = """
            {
                "id": "testprojectid",
                "createdBy": "testuserid",
                "projectId": "Test Project ID",
                "projectName": "Test Project Name",
                "createdAt": "0001-01-01",
                "plannedStartDate": "0001-01-01",
                "plannedFinishDate": "9999-01-01",
                "projectStatus": "CURRENT",
                "assessorName": "Test Assessor",
                "projectDetails": "Test Details",
                "documentIds": [""" + "\"" + fileId + "\"" + """
            ]}
            """;

        mvc.perform(put("/api/projects/testprojectid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectUpdate))
                .andExpect(status().isOk());

        mvc.perform(get("/api/files/projects/testprojectid/metadata"))
                .andExpect(status().isOk());
    }
}