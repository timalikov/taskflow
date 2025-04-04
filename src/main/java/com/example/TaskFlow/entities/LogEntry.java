package com.example.TaskFlow.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "log_entry")
@Getter
@Setter
public class LogEntry {
    @Id
    private ObjectId id;

    private String level;

    private String message;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private Map<String, Object> context;
}