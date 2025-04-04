package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.responses.documentFiles.DocumentFileResponse;
import com.example.TaskFlow.services.DocumentFileService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/documents")
public class DocumentFileController {
    private final DocumentFileService documentFileService;

    @PostMapping("/tasks/{id}")
    public ResponseEntity<DocumentFileResponse> saveDocument(
            @PathVariable Long id,
            @RequestPart MultipartFile file ) throws IOException
    {
        var response = documentFileService.saveDocument(id, file);
        var location = URI.create("/api/documents/%s".formatted(response.getId()));

        return ResponseEntity.created(location)
                .body(DocumentFileResponse.fromEntity(response));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<DocumentFileResponse>> getTaskDocument(@PathVariable Long id) {

        return ResponseEntity.ok(documentFileService.getDocumentsByTask(id)
                .stream().map(DocumentFileResponse::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String id) throws IOException {

        var response = documentFileService.downloadDocument(id);
        var fileName = Optional.ofNullable(response.getFilename()).orElse("file");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(fileName))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        documentFileService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
