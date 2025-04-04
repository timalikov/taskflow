package com.example.TaskFlow.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "document_files")
@Getter
@Setter
public class DocumentFile {
    @Id
    private ObjectId id;

    private Long taskId;

    private String fileName;

    private String fileType;

    @CreationTimestamp
    private LocalDateTime uploadedAt;

    private Long size;
}
