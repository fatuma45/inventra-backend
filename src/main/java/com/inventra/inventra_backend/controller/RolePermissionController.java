package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.AssignPermissionsRequest;
import com.inventra.inventra_backend.dto.response.PermissionResponse;
import com.inventra.inventra_backend.service.RolePermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @PostMapping("/{roleId}/permissions")
    public List<PermissionResponse> assignPermissions(
            @PathVariable String roleId,
            @RequestBody AssignPermissionsRequest request) {

        return rolePermissionService.assignPermissions(roleId, request);
    }

    @GetMapping("/{roleId}/permissions")
    public List<PermissionResponse> getRolePermissions(
            @PathVariable String roleId) {

        return rolePermissionService.getRolePermissions(roleId);
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public void removePermission(
            @PathVariable String roleId,
            @PathVariable String permissionId) {

        rolePermissionService.removePermission(roleId, permissionId);
    }
}
