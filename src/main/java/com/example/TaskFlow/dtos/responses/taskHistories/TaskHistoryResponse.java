package com.example.TaskFlow.dtos.responses.taskHistories;

import com.example.TaskFlow.entities.TaskHistory;

import java.time.LocalDateTime;
import java.util.Map;

public record TaskHistoryResponse (
        String id,
        Long taskId,
        String action,
        Long performedBy,
        LocalDateTime timestamp,
        Map<String, Object> details
) {
    public static TaskHistoryResponse fromEntity(TaskHistory taskHistory) {
        return new TaskHistoryResponse(taskHistory.getId().toString(), taskHistory.getTaskId(), taskHistory.getAction(), taskHistory.getPerformedBy(), taskHistory.getTimestamp(), taskHistory.getDetails());
    }
}
