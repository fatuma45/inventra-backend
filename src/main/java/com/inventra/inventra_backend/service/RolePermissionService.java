package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.AssignPermissionsRequest;
import com.inventra.inventra_backend.dto.response.PermissionResponse;

import java.util.List;

public interface RolePermissionService {

    List<PermissionResponse> assignPermissions(
            String roleId,
            AssignPermissionsRequest request
    );

    List<PermissionResponse> getRolePermissions(String roleId);

    void removePermission(String roleId, String permissionId);
}