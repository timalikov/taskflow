package com.example.TaskFlow.repositories;


import com.example.TaskFlow.entities.DocumentFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentFileRepository extends MongoRepository<DocumentFile, ObjectId> {

    List<DocumentFile> findAllByTaskId(Long taskId);
}
