package com.example.TaskFlow.utils;

import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityUtils {
    public static <T, Long> T getByIdOrThrow(JpaRepository<T, Long> repository, Long id, String entityName){
        System.out.println("Task not found");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("%s with id: %s not found".formatted(entityName, id)));
    }
}