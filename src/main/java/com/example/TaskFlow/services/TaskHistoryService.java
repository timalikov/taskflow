package com.example.TaskFlow.services;

import com.example.TaskFlow.entities.TaskHistory;

import java.util.List;

public interface TaskHistoryService {
    List<TaskHistory> getAllTaskHistory(Long id);
}
