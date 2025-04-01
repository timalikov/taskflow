package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.responses.documentFiles.DocumentFileResponse;
import com.example.TaskFlow.entities.DocumentFile;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import com.example.TaskFlow.repositories.DocumentFileRepository;
import com.example.TaskFlow.services.DocumentFileService;
import com.mongodb.client.gridfs.model.GridFSFile;

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

    @Override
    public DocumentFileResponse saveDocument(Long id, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }

        ObjectId objectId = gridFsTemplate.store(
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

        return DocumentFileResponse.fromEntity(documentFile);
    }

    @Override
    public List<DocumentFileResponse> getDocumentsByTask(Long id) {
        List<DocumentFile> documentFiles = documentFileRepository.findAllByTaskId(id);

        return documentFiles.stream()
                .map(DocumentFileResponse::fromEntity)
                .toList();
    }

    @Override
    public Resource downloadDocument(String id) throws IOException {
        GridFSFile file = findFileById(id);
        return gridFsTemplate.getResource(file);
    }

    @Override
    public void deleteDocument(String id) {
        findFileById(id);
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(new ObjectId(id))));
        documentFileRepository.deleteById(new ObjectId(id));
    }

    private GridFSFile findFileById(String id) {
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        return Optional.ofNullable(gridFsTemplate.findOne(query))
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
    }

}
