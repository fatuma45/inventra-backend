package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.PermissionRequest;
import com.inventra.inventra_backend.dto.response.PermissionResponse;
import com.inventra.inventra_backend.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionResponse createPermission(@RequestBody PermissionRequest request) {
        return permissionService.createPermission(request);
    }

    @PutMapping("/{permissionId}")
    public PermissionResponse updatePermission(
            @PathVariable String permissionId,
            @RequestBody PermissionRequest request) {

        return permissionService.updatePermission(permissionId, request);
    }

    @GetMapping("/{permissionId}")
    public PermissionResponse getPermissionById(@PathVariable String permissionId) {
        return permissionService.getPermissionById(permissionId);
    }

    @GetMapping
    public List<PermissionResponse> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(@PathVariable String permissionId) {
        permissionService.deletePermission(permissionId);
    }
}