package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.entities.TaskHistory;
import com.example.TaskFlow.repositories.TaskHistoryRepository;
import com.example.TaskFlow.services.TaskHistoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<TaskHistory> getAllTaskHistory(Long id) {
        return taskHistoryRepository.findAllByTaskId(id);
    }
}
