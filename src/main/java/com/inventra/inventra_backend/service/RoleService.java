package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.RoleRequest;
import com.inventra.inventra_backend.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    RoleResponse updateRole(String roleId, RoleRequest request);

    RoleResponse getRoleById(String roleId);

    List<RoleResponse> getAllRoles();

    void deleteRole(String roleId);
}
