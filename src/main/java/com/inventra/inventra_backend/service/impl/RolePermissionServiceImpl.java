package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.AssignPermissionsRequest;
import com.inventra.inventra_backend.dto.response.PermissionResponse;
import com.inventra.inventra_backend.entity.Permission;
import com.inventra.inventra_backend.entity.Role;
import com.inventra.inventra_backend.mapper.PermissionMapper;
import com.inventra.inventra_backend.repository.PermissionRepository;
import com.inventra.inventra_backend.repository.RoleRepository;
import com.inventra.inventra_backend.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionServiceImpl(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository) {

        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<PermissionResponse> assignPermissions(
            String roleId,
            AssignPermissionsRequest request) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        Set<Permission> permissions = request.getPermissionIds()
                .stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() ->
                                new RuntimeException("Permission not found: " + permissionId)))
                .collect(Collectors.toSet());

        role.getPermissions().addAll(permissions);

        roleRepository.save(role);

        return role.getPermissions()
                .stream()
                .map(PermissionMapper::toResponse)
                .toList();
    }

    @Override
    public List<PermissionResponse> getRolePermissions(String roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        return role.getPermissions()
                .stream()
                .map(PermissionMapper::toResponse)
                .toList();
    }

    @Override
    public void removePermission(String roleId, String permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() ->
                        new RuntimeException("Permission not found."));

        role.getPermissions().remove(permission);

        roleRepository.save(role);
    }
}
