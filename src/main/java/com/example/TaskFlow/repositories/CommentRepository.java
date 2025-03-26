package com.example.TaskFlow.repositories;

import com.example.TaskFlow.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
