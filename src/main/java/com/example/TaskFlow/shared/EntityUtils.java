package com.example.TaskFlow.shared;

import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityUtils {
    public static <T, Long> T getByIdOrThrow(JpaRepository<T, Long> repository, Long id, String entityName){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " with id: " + id + " not found"));
    }
}
