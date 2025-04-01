package com.example.TaskFlow.services;

import com.example.TaskFlow.entities.LogEntry;

import java.util.List;

public interface LogEntryService {
    List<LogEntry> getAllLogEntries();
    List<LogEntry> getAllLogsByLevel(String level);
}
