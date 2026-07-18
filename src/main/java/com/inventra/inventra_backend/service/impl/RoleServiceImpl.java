package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.RoleRequest;
import com.inventra.inventra_backend.dto.response.RoleResponse;
import com.inventra.inventra_backend.entity.Role;
import com.inventra.inventra_backend.repository.RoleRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public RoleServiceImpl(
            RoleRepository roleRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.roleRepository = roleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {

        if (roleRepository.existsByRoleName(request.getRoleName())) {
            throw new RuntimeException("Role already exists.");
        }

        Role role = new Role();

        role.setRoleId(sequenceGeneratorService.generateId("ROL"));
        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setActive(request.getActive());

        Role savedRole = roleRepository.save(role);

        return mapToResponse(savedRole);
    }

    @Override
    public RoleResponse updateRole(String roleId, RoleRequest request) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        if (!role.getRoleName().equalsIgnoreCase(request.getRoleName())
                && roleRepository.existsByRoleName(request.getRoleName())) {

            throw new RuntimeException("Role name already exists.");
        }

        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setActive(request.getActive());

        Role updatedRole = roleRepository.save(role);

        return mapToResponse(updatedRole);
    }

    @Override
    public RoleResponse getRoleById(String roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        if (Boolean.TRUE.equals(role.getDeleted())) {
            throw new RuntimeException("Role has been deleted.");
        }

        return mapToResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRole(String roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException("Role not found."));

        role.setDeleted(true);
        role.setActive(false);

        roleRepository.save(role);
    }

    private RoleResponse mapToResponse(Role role) {

        RoleResponse response = new RoleResponse();

        response.setRoleId(role.getRoleId());
        response.setRoleName(role.getRoleName());
        response.setDescription(role.getDescription());
        response.setActive(role.getActive());

        return response;
    }
}
