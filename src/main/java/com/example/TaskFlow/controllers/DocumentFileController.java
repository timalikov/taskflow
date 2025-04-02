package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.responses.documentFiles.DocumentFileResponse;
import com.example.TaskFlow.services.DocumentFileService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentFileController {
    private final DocumentFileService documentFileService;

    @PostMapping("/tasks/{id}/documents")
    public ResponseEntity<DocumentFileResponse> saveDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file ) throws IOException {
        String contentType = file.getContentType();
        List<String> allowedTypes = List.of("application/pdf", "image/png", "image/jpeg", "image/jpg");
        if (!allowedTypes.contains(contentType)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(documentFileService.saveDocument(id, file));
    }

    @GetMapping("/tasks/{id}/documents")
    public ResponseEntity<List<DocumentFileResponse>> getTaskDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentFileService.getDocumentsByTask(id));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String id) throws IOException {
        if (!ObjectId.isValid(id)) throw new IllegalArgumentException("Invalid document id format");

        Resource resource = documentFileService.downloadDocument(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        if (!ObjectId.isValid(id)) throw new IllegalArgumentException("Invalid document id format");

        documentFileService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }
}
