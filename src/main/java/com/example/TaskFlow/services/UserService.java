package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.users.CreateUserRequest;
import com.example.TaskFlow.dtos.requests.users.UpdateUserRequest;
import com.example.TaskFlow.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
