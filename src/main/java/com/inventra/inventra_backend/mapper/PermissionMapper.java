package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.PermissionResponse;
import com.inventra.inventra_backend.entity.Permission;

public class PermissionMapper {

    private PermissionMapper() {
    }

    public static PermissionResponse toResponse(Permission permission) {

        PermissionResponse response = new PermissionResponse();

        response.setPermissionId(permission.getPermissionId());
        response.setPermissionName(permission.getPermissionName());
        response.setDescription(permission.getDescription());
        response.setActive(permission.getActive());

        return response;
    }
}
