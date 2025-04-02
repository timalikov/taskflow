package com.example.TaskFlow.repositories;

import com.example.TaskFlow.entities.LogEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface LogEntryRepository extends MongoRepository<LogEntry, ObjectId> {
    List<LogEntry> findAllByLevel(String level);
}
