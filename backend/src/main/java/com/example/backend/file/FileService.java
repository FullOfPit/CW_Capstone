package com.example.backend.file;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.gridfs.GridFSFindIterable;
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
    private static final String createdBy = "createdBy";

    public GridFsResource getResource(String id) {
        return gridFsTemplate.getResource(getFile(id));
    }

    public FileMetadata getFileMetadata(String id) {

        GridFSFile gridFSFile = getFile(id);
        String contentType = "_contentType";
        Document metadata = Optional.ofNullable(
                        gridFSFile.getMetadata())
                .orElse(new Document(Map.of(contentType, "", createdBy, "")));

        return new FileMetadata(
                id,
                gridFSFile.getFilename(),
                metadata.getString(contentType),
                gridFSFile.getLength(),
                metadata.getString(createdBy)
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

    public List<GridFSFile> getFilesByProjectId(String projectId) {

        List<GridFSFile> fileList = new ArrayList<>();

        GridFSFindIterable gridFSFindIterable = Optional.of(
                gridFsTemplate.find(
                Query.query(Criteria.where("metadata.createdBy").is(projectId)))
                ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        gridFSFindIterable.forEach(fileList::add);

        return fileList;
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
                        .add(createdBy, projectId)
                        .get()
        );
        return getFileMetadata(objectId.toString());
    }

    public void deleteById(String id) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
    }

    public List<FileMetadata> getFileMetadataByProjectId(String projectId) {

        List<GridFSFile> listOfFiles = this.getFilesByProjectId(projectId);

        return listOfFiles.stream()
                .map(file -> {
                    assert file.getMetadata() != null;
                    return new FileMetadata(
                            file.getId().toString(),
                            file.getFilename(),
                            file.getMetadata().getString("_contentType"),
                            file.getLength(),
                            file.getMetadata().getString(createdBy));
                }).toList();
    }
}