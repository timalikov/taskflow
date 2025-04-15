package com.example.TaskFlow.repositories;

import com.example.TaskFlow.entities.TaskHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataMongoTest
public class TaskHistoryRepositoryTest {

    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    private final Long taskId1 = 101L;
    private final Long taskId2 = 102L;
    private final Long nonExistentTaskId = 999L;
    private final String taskTitle = "Test Task title";

    @BeforeEach
    void setUp() {
        taskHistoryRepository.deleteAll();

        var taskHistory1 = new TaskHistory(); taskHistory1.setTaskId(taskId1);
        var taskHistory2 = new TaskHistory(); taskHistory2.setTaskId(taskId1);
        var taskHistory3 = new TaskHistory(); taskHistory3.setTaskId(taskId2);

        taskHistoryRepository.save(taskHistory1);
        taskHistoryRepository.save(taskHistory2);
        taskHistoryRepository.save(taskHistory3);
    }

    @Test
    void shouldFindAllByTaskId() {
        var result = taskHistoryRepository.findAllByTaskId(taskId1);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(taskHistory -> taskHistory.getTaskId().equals(taskId1)));
    }

    @Test
    void shouldReturnEmptyWhenNoMatch() {
        var result = taskHistoryRepository.findAllByTaskId(nonExistentTaskId);

        assertTrue(result.isEmpty());
    }

}
