package com.example.TaskFlow.dtos.responses.logEntries;

import com.example.TaskFlow.entities.LogEntry;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;

public record LogEntryResponse(
        ObjectId id,
        String level,
        String message,
        LocalDateTime timestamp,
        Map<String, Object> context
) {
    public static LogEntryResponse fromEntity(LogEntry logEntry) {
        return new LogEntryResponse(logEntry.getId(), logEntry.getLevel(), logEntry.getMessage(), logEntry.getTimestamp(), logEntry.getContext());
    }
}
