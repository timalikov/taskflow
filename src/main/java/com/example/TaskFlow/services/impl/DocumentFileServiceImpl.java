package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.entities.DocumentFile;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import com.example.TaskFlow.properties.UploadProperties;
import com.example.TaskFlow.repositories.DocumentFileRepository;
import com.example.TaskFlow.services.DocumentFileService;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.example.TaskFlow.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentFileServiceImpl implements DocumentFileService {
    private final GridFsTemplate gridFsTemplate;
    private final DocumentFileRepository documentFileRepository;
    private final UploadProperties uploadProperties;

    @Override
    public DocumentFile saveDocument(Long id, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty or null");
        }

        var contentType = file.getContentType();
        if (!this.uploadProperties.getAllowedFileTypes().contains(contentType)) {
            throw new BadRequestException("File type is not supported");
        }

        var objectId = gridFsTemplate.store(
                file.getInputStream(),
                Optional.ofNullable(file.getOriginalFilename()).orElse("unknown"),
                Optional.ofNullable(file.getContentType()).orElse("application/octet-stream")
        );

        DocumentFile documentFile = new DocumentFile();
        documentFile.setId(objectId);
        documentFile.setFileName(file.getOriginalFilename());
        documentFile.setFileType(file.getContentType());
        documentFile.setTaskId(id);
        documentFile.setSize(file.getSize());
        documentFileRepository.save(documentFile);

        return documentFile;
    }

    @Override
    public List<DocumentFile> getDocumentsByTask(Long id) {
        return documentFileRepository.findAllByTaskId(id);
    }

    @Override
    public Resource downloadDocument(String id) {
        var file = findFileById(id);
        return gridFsTemplate.getResource(file);
    }

    @Override
    public void deleteDocument(String id) {
        findFileById(id);
        var objectId = parseObjectId(id);

        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(objectId)));
        documentFileRepository.deleteById(objectId);
    }

    private GridFSFile findFileById(String id) {
        var objectId = parseObjectId(id);

        Query query = Query.query(Criteria.where("_id").is(objectId));

        var file = gridFsTemplate.findOne(query);
        if (file == null) throw new ResourceNotFoundException("Document not found with id: %s".formatted(id));

        return file;
    }

    private ObjectId parseObjectId(String id) {
        if (!ObjectId.isValid(id)) throw new IllegalArgumentException("Invalid document id format");

        return new ObjectId(id);
    }

}
