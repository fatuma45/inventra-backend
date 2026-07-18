package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.CreateUserRequest;
import com.inventra.inventra_backend.dto.request.UpdateUserRequest;
import com.inventra.inventra_backend.dto.response.UserResponse;
import com.inventra.inventra_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(
            @PathVariable String userId,
            @RequestBody UpdateUserRequest request) {

        return userService.updateUser(userId, request);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/{userId}/activate")
    public void activateUser(@PathVariable String userId) {
        userService.activateUser(userId);
    }

    @PatchMapping("/{userId}/deactivate")
    public void deactivateUser(@PathVariable String userId) {
        userService.deactivateUser(userId);
    }

    @PatchMapping("/{userId}/lock")
    public void lockUser(@PathVariable String userId) {
        userService.lockUser(userId);
    }

    @PatchMapping("/{userId}/unlock")
    public void unlockUser(@PathVariable String userId) {
        userService.unlockUser(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
}
