package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.PermissionRequest;
import com.inventra.inventra_backend.dto.response.PermissionResponse;
import com.inventra.inventra_backend.entity.Permission;
import com.inventra.inventra_backend.mapper.PermissionMapper;
import com.inventra.inventra_backend.repository.PermissionRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public PermissionServiceImpl(
            PermissionRepository permissionRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.permissionRepository = permissionRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {

        if (permissionRepository.existsByPermissionName(request.getPermissionName())) {
            throw new RuntimeException("Permission already exists.");
        }

        Permission permission = new Permission();

        permission.setPermissionId(sequenceGeneratorService.generateId("PER"));
        permission.setPermissionName(request.getPermissionName());
        permission.setDescription(request.getDescription());
        permission.setActive(request.getActive());

        permissionRepository.save(permission);

        return PermissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse updatePermission(String permissionId, PermissionRequest request) {

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found."));

        permissionRepository.findByPermissionName(request.getPermissionName())
                .ifPresent(existing -> {
                    if (!existing.getPermissionId().equals(permissionId)) {
                        throw new RuntimeException("Permission name already exists.");
                    }
                });

        permission.setPermissionName(request.getPermissionName());
        permission.setDescription(request.getDescription());
        permission.setActive(request.getActive());

        permissionRepository.save(permission);

        return PermissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse getPermissionById(String permissionId) {

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found."));

        if (permission.getDeleted()) {
            throw new RuntimeException("Permission not found.");
        }

        return PermissionMapper.toResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .filter(permission -> !permission.getDeleted())
                .map(PermissionMapper::toResponse)
                .toList();
    }

    @Override
    public void deletePermission(String permissionId) {

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found."));

        permission.setDeleted(true);
        permission.setActive(false);

        permissionRepository.save(permission);
    }
}
