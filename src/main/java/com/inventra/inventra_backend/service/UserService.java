package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.CreateUserRequest;
import com.inventra.inventra_backend.dto.request.UpdateUserRequest;
import com.inventra.inventra_backend.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(String userId, UpdateUserRequest request);

    UserResponse getUserById(String userId);

    List<UserResponse> getAllUsers();

    void activateUser(String userId);

    void deactivateUser(String userId);

    void lockUser(String userId);

    void unlockUser(String userId);

    void deleteUser(String userId);
}
