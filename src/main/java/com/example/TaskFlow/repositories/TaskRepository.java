package com.example.TaskFlow.repositories;

import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAll(Pageable pageable);

    List<Task> findAllByUser(User user);
}
