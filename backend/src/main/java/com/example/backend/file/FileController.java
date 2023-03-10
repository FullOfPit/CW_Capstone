package com.example.backend.file;

import com.example.backend.exception.ProjectNotRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getFile (@PathVariable String id) throws IOException {
        GridFsResource gridFsResource = this.fileService.getResource(id);


        return  ResponseEntity.ok().contentType(
                        MediaType.valueOf(gridFsResource.getContentType()))
                .body(new InputStreamResource(gridFsResource.getInputStream()));
    }

    @GetMapping("/{id}/metadata")
    public FileMetadata getFileMetadata(@PathVariable String id) {
        return this.fileService.getFileMetadata(id);
    }

    @GetMapping("/projects/{projectId}/metadata")
    public List<FileMetadata> getFileMetadataByProjectId(@PathVariable String projectId) throws ProjectNotRegisteredException {
        return this.fileService.getFileMetadataByProjectId(projectId);
    }

    @PostMapping("/{projectId}")
    public FileMetadata uploadFile(@PathVariable String projectId, @RequestParam("file") MultipartFile file) throws IOException {
        return this.fileService.saveFile(projectId, file);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        this.fileService.deleteById(id);
    }
}
