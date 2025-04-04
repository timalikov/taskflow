package com.example.TaskFlow.services;

import com.example.TaskFlow.entities.DocumentFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentFileService {
    DocumentFile saveDocument(Long id, MultipartFile file) throws IOException;
    List<DocumentFile> getDocumentsByTask(Long id);
    Resource downloadDocument(String id) throws IOException;
    void deleteDocument(String id);
}