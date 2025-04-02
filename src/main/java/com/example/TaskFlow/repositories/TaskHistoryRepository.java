package com.example.TaskFlow.repositories;

import com.example.TaskFlow.entities.TaskHistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskHistoryRepository extends MongoRepository<TaskHistory, ObjectId> {
    List<TaskHistory> findAllByTaskId(Long taskId);
}
