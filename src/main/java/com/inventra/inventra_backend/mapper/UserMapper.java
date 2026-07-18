package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.UserResponse;
import com.inventra.inventra_backend.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setUserId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setPhoneNumber(user.getPhoneNumber());

        if (user.getRole() != null) {
            response.setRole(user.getRole().getRoleName());
        }

        response.setActive(user.getActive());

        return response;
    }
}
