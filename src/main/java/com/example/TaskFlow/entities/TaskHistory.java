package com.example.TaskFlow.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "task_history")
@Getter
@Setter
public class TaskHistory {
    @Id
    private ObjectId id;

    private Long taskId;

    private String action;

    private Long performedBy;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private Map<String, Object> details;
}