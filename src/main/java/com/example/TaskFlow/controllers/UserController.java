package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.requests.users.CreateUserRequest;
import com.example.TaskFlow.dtos.requests.users.UpdateUserRequest;
import com.example.TaskFlow.dtos.responses.users.CreateUserResponse;
import com.example.TaskFlow.dtos.responses.users.GetUserResponse;
import com.example.TaskFlow.dtos.responses.users.UpdateUserResponse;
import com.example.TaskFlow.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<GetUserResponse>> getAllUsers(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(
                userService.getAllUsers(pageable)
                        .map(GetUserResponse::fromEntity)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                GetUserResponse.fromEntity(userService.getUserById(id))
        );
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CreateUserResponse.fromEntity(userService.createUser(request))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(
                UpdateUserResponse.fromEntity(userService.updateUser(id, request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
