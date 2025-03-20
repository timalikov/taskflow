package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.requests.users.CreateUserRequest;
import com.example.TaskFlow.dtos.requests.users.UpdateUserRequest;
import com.example.TaskFlow.dtos.responses.users.CreateUserResponse;
import com.example.TaskFlow.dtos.responses.users.GetUserResponse;
import com.example.TaskFlow.dtos.responses.users.UpdateUserResponse;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.repositories.UserRespository;
import com.example.TaskFlow.services.UserService;
import com.example.TaskFlow.shared.EntityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;

    @Override
    public Page<GetUserResponse> getAllUsers(Pageable pageable) {
        return userRespository.findAll(pageable).map(GetUserResponse::fromEntity);
    }

    @Override
    public GetUserResponse getUserById(Long id) {
        return GetUserResponse.fromEntity(EntityUtils.getByIdOrThrow(userRespository, id, "User"));
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());

        return CreateUserResponse.fromEntity(userRespository.save(user));
    }

    @Override
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = EntityUtils.getByIdOrThrow(userRespository, id, "User");

        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.email() != null) user.setEmail(request.email());

        return UpdateUserResponse.fromEntity(userRespository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = EntityUtils.getByIdOrThrow(userRespository, id, "User");
        userRespository.delete(user);
    }
}
