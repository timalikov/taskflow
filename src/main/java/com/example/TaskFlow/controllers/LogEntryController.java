package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.responses.logEntries.LogEntryResponse;
import com.example.TaskFlow.services.LogEntryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogEntryController {
    private final LogEntryService logEntryService;

    @GetMapping
    public ResponseEntity<List<LogEntryResponse>> getAllLogEntries(@RequestParam(required = false) String level) {

        var logs = (level == null) ? logEntryService.getAllLogEntries()
                : logEntryService.getAllLogsByLevel(level);

        return ResponseEntity.ok(logs
                .stream().map(LogEntryResponse::fromEntity)
                .toList());
    }

}
