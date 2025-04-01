package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.responses.documentFiles.DocumentFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentFileService {
    DocumentFileResponse saveDocument(Long id, MultipartFile file) throws IOException;
    List<DocumentFileResponse> getDocumentsByTask(Long id);
    Resource downloadDocument(String id) throws IOException;
    void deleteDocument(String id);
}