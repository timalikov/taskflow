package com.example.TaskFlow.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "document_files")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class DocumentFile {
    @Id
    private ObjectId id;


    private Long taskId;
    private String fileName;
    private String fileType;

    @CreatedDate
    private LocalDateTime uploadedAt;
    private Long size;
}
