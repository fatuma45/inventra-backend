package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.RoleResponse;
import com.inventra.inventra_backend.entity.Role;

public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleResponse toResponse(Role role) {

        RoleResponse response = new RoleResponse();

        response.setRoleId(role.getRoleId());
        response.setRoleName(role.getRoleName());
        response.setDescription(role.getDescription());


        return response;
    }
}