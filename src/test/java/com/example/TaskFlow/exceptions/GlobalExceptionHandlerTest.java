package com.example.TaskFlow.exceptions;

import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskRepository taskRepository;

    private final Long nonExistentTaskId = 999L;

    @Test
    void shouldReturn404ForResourceNotFound() throws Exception {
        mockMvc.perform(get("/api/tasks/%d".formatted(nonExistentTaskId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Task with id: %d not found".formatted(nonExistentTaskId)));
    }

    @Test
    void shouldReturn400WhenValidationFailed() throws Exception {
        var invalidJson = """
            {
                "title" : "",
                "description": "desc"
            }
        """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturn400WhenFileIsEmpty() throws Exception {
        when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.of(new Task()));

        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "application/pdf", new byte[0]);

        mockMvc.perform(multipart("/api/documents/tasks/{id}", nonExistentTaskId)
                        .file(emptyFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("File is empty or null"));
    }

}
