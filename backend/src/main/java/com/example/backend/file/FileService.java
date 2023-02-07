package com.example.backend.file;

import com.example.backend.exception.ProjectNotRegisteredException;
import com.example.backend.project.Project;
import com.example.backend.project.ProjectService;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final GridFsTemplate gridFsTemplate;
    private final ProjectService projectService;

    public GridFsResource getResource(String id) {

        return gridFsTemplate.getResource(getFile(id));
    }

    public FileMetadata getFileMetadata(String id) {

        GridFSFile gridFSFile = getFile(id);
        Document metadata = Optional.ofNullable(
                        gridFSFile.getMetadata())
                .orElse(new Document(Map.of("_contentType", "", "createdBy", "")));

        return new FileMetadata(
                id,
                gridFSFile.getFilename(),
                metadata.getString("_contentType"),
                gridFSFile.getLength(),
                metadata.getString("createdBy")
        );
    }

    public GridFSFile getFile(String id) {

        return Optional.ofNullable(gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(id)))).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "File not found"
                )
        );
    }

    public FileMetadata saveFile(String projectId, MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is Empty");
        }

        ObjectId objectId = gridFsTemplate.store(
                multipartFile.getInputStream(),
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                //userID needs to be the actual users ID
                BasicDBObjectBuilder.start()
                        .add("createdBy", projectId)
                        .get()
        );
        return getFileMetadata(objectId.toString());
    }

    public void deleteById(String id) {

        gridFsTemplate.delete(Query.query(Criteria.where("files_id").is(id)));
    }

    public List<FileMetadata> getFileMetadataByProjectId(String projectId) throws ProjectNotRegisteredException {

        Project project = Optional.ofNullable(
                this.projectService.getById(projectId))
                .orElseThrow(ProjectNotRegisteredException::new);

        List<FileMetadata> fileMetadataList = new ArrayList<>();

        project.getDocumentIds().forEach((fileId) -> fileMetadataList.add(getFileMetadata(fileId)));

        return fileMetadataList;
    }
}