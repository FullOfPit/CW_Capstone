package com.example.backend.file;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.BsonString;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static org.mockito.Mockito.*;

class FileServiceTest {

    //                          -- Auxiliary

    Document testDocument = new Document(
            Map.of("_contentType",
                    "application/pdf",
                    "createdBy",
                    "testuserid"));

    GridFSFile testGridFSFile = new GridFSFile(
            new BsonString("testgridfileid"),
            "Test File Name",
            1L,
            1,
            Date.from(Instant.parse("0001-01-01T01:01:01.001+00:00")),
            testDocument
            );

    GridFSFile testGridFSFileNoMeta = new GridFSFile(
            new BsonString("testgridfileid"),
            "Test File Name",
            1L,
            1,
            Date.from(Instant.parse("0001-01-01T01:01:01.001+00:00")),
            null
    );

    @Test
    void getFile_FromId_Returns404WhenIdNotRegistered() {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);
        when(gridFsTemplate.findOne(
                Query.query(Criteria.where("id")
                        .is("testgridfileid"))))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        //When
        FileService fileService = new FileService(gridFsTemplate);
        //Then
        try {
            fileService.getFile("testgridfileid");
        } catch (ResponseStatusException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    void getFile_FromId_ReturnsFile() {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);
        when(gridFsTemplate.findOne(
                Query.query(Criteria.where("_id")
                        .is("testgridfileid"))))
                .thenReturn(testGridFSFile);

        //When
        FileService fileService = new FileService(gridFsTemplate);
        GridFSFile actual = fileService.getFile("testgridfileid");

        //Then
        Assertions.assertEquals(testGridFSFile, actual);
    }

    @Test
    void getFileMetadata_ReturnsDummyDocument_WhenFileNotFound() {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);
        when(gridFsTemplate.findOne(
                Query.query(Criteria.where("_id")
                        .is("testgridfileid"))))
                .thenReturn(null);
        //When
        FileService fileService = new FileService(gridFsTemplate);
        //Then
        try {
            fileService.getFile("testgridfileid");
            Assertions.fail();
        } catch (ResponseStatusException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    void getFileMetadata_ReturnsFileMetadata_WhenFileFound() {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);
        when(gridFsTemplate.findOne(
                Query.query(Criteria.where("_id")
                        .is("testgridfileid"))))
                .thenReturn(testGridFSFile);

        //When
        FileService fileService = new FileService(gridFsTemplate);
        FileMetadata actual = fileService.getFileMetadata("testgridfileid");
        //Then
        Assertions.assertEquals(
                new FileMetadata(
                        "testgridfileid",
                        "Test File Name",
                        "application/pdf",
                        1L,
                        "testuserid")
                , actual);
    }

    @Test
    void getFileMetadata_ReturnsDummyMetadata_WhenNotMetadataInFile() {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);
        when(gridFsTemplate.findOne(
                Query.query(Criteria.where("_id")
                        .is("testgridfileid"))))
                .thenReturn(testGridFSFileNoMeta);

        //When
        FileService fileService = new FileService(gridFsTemplate);
        FileMetadata actual = fileService.getFileMetadata("testgridfileid");
        //Then
        Assertions.assertEquals(
                new FileMetadata("testgridfileid",
                        "Test File Name",
                        "",
                        1L,
                        "")
                , actual);
    }

    /*
    @Test
    void getFileMetadataByProjectId_ReturnsListMetadataFromProject() throws ProjectNotRegisteredException {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);
        ProjectService projectService = mock(ProjectService.class);
        GridFSFindIterable mockGridFSFindIterable = mock(GridFSFindIterable.class);
        when(projectService.getById("testprojectid")).thenReturn(testProject);
        when(gridFsTemplate.find(any())).thenReturn(mockGridFSFindIterable);
        when(mockGridFSFindIterable.iterator()).thenReturn(Collections.singletonList(testGridFSFile).iterator());
        //When
        FileService fileService = new FileService(gridFsTemplate);
        List<FileMetadata> actual = fileService.getFileMetadataByProjectId("testprojectid");
        //Then
        Assertions.assertEquals(
                List.of(
                        new FileMetadata(
                                "testgridfileid",
                                "Test File Name",
                                "application/pdf",
                                1L,
                                "testuserid"))
                , actual);
    }
     */
    @Test
    void saveFile_Returns401WhenMultipartFileEmpty() throws IOException {
        //Given
        GridFsTemplate gridFsTemplate = mock(GridFsTemplate.class);

        MockMultipartFile testMockMultipartFile = new MockMultipartFile(
                "testmockdata",
                "testmockfilename",
                "testmockformdata",
                new byte[0]);
        //When
        FileService fileService = new FileService(gridFsTemplate);
        //Then
        try {
            fileService.saveFile("testprojectid", testMockMultipartFile);
            Assertions.fail();
        } catch (ResponseStatusException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

}
