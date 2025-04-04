package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.requests.users.CreateUserRequest;
import com.example.TaskFlow.dtos.requests.users.UpdateUserRequest;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.repositories.UserRespository;
import com.example.TaskFlow.services.UserService;
import com.example.TaskFlow.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;

    @Transactional(readOnly = true)
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRespository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long id) {
        return EntityUtils.getByIdOrThrow(userRespository, id, "User");
    }

    @Override
    public User createUser(CreateUserRequest request) {
        var user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());

        return userRespository.save(user);
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        var user = EntityUtils.getByIdOrThrow(userRespository, id, "User");

        if (request.firstName() != null) user.setFirstName(request.firstName());
        if (request.lastName() != null) user.setLastName(request.lastName());
        if (request.email() != null) user.setEmail(request.email());

        return userRespository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRespository.delete(EntityUtils.getByIdOrThrow(userRespository, id, "User"));
    }
}
