package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.PermissionRequest;
import com.inventra.inventra_backend.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);

    PermissionResponse updatePermission(String permissionId, PermissionRequest request);

    PermissionResponse getPermissionById(String permissionId);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(String permissionId);
}
