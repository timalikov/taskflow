package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.users.CreateUserRequest;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.repositories.UserRepository;
import com.example.TaskFlow.services.impl.UserServiceImpl;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    private final String firstName = "first name";
    private final String lastName = "last name";
    private final String email = "test@mail.com";

    @Test
    void shouldCreateUser() {
        var request = new CreateUserRequest(
                firstName,
                lastName,
                email
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        var result = userService.createUser(request);

        verify(userRepository).save(userCaptor.capture());
        var savedUser = userCaptor.getValue();

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(request.firstName(), savedUser.getFirstName()),
            () -> assertEquals(request.lastName(), savedUser.getLastName()),
            () -> assertEquals(request.email(), savedUser.getEmail())
        );

        verify(userRepository).existsByEmail(request.email());
    }

    @Test
    void shouldReturnUserById() {
        var user = createMockUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var result = userService.getUserById(user.getId());

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(user.getId(), result.getId()),
            () -> assertEquals(user.getFirstName(), result.getFirstName()),
            () -> assertEquals(user.getLastName(), result.getLastName()),
            () -> assertEquals(user.getEmail(), result.getEmail())
        );

        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        var request = new CreateUserRequest(
                firstName,
                lastName,
                email
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(ValidationException.class, () -> userService.createUser(request));

        verify(userRepository).existsByEmail(request.email());
        verify(userRepository, never()).save(any());
    }

    private User createMockUser() {
        var user = new User();
        user.setId(1L);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

}
