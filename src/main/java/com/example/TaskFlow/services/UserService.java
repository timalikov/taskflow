package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.users.CreateUserRequest;
import com.example.TaskFlow.dtos.requests.users.UpdateUserRequest;
import com.example.TaskFlow.dtos.responses.users.CreateUserResponse;
import com.example.TaskFlow.dtos.responses.users.GetUserResponse;
import com.example.TaskFlow.dtos.responses.users.UpdateUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<GetUserResponse> getAllUsers(Pageable pageable);
    GetUserResponse getUserById(Long id);
    CreateUserResponse createUser(CreateUserRequest request);
    UpdateUserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
