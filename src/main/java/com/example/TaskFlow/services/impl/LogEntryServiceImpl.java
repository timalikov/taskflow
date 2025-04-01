package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.entities.LogEntry;
import com.example.TaskFlow.repositories.LogEntryRepository;
import com.example.TaskFlow.services.LogEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogEntryServiceImpl implements LogEntryService {
    private final LogEntryRepository logEntryRepository;


    @Override
    public List<LogEntry> getAllLogEntries() {
        return logEntryRepository.findAll();
    }

    @Override
    public List<LogEntry> getAllLogsByLevel(String level) {
        return logEntryRepository.findAllByLevel(level);
    }
}
