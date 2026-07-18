package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.RoleRequest;
import com.inventra.inventra_backend.dto.response.RoleResponse;
import com.inventra.inventra_backend.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse createRole(@Valid @RequestBody RoleRequest request) {
        return roleService.createRole(request);
    }

    @PutMapping("/{roleId}")
    public RoleResponse updateRole(
            @PathVariable String roleId,
            @Valid @RequestBody RoleRequest request) {

        return roleService.updateRole(roleId, request);
    }

    @GetMapping("/{roleId}")
    public RoleResponse getRoleById(@PathVariable String roleId) {
        return roleService.getRoleById(roleId);
    }

    @GetMapping
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable String roleId) {
        roleService.deleteRole(roleId);
    }
}