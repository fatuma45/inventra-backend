package com.inventra.inventra_backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PermissionRequest {

    @NotBlank
    private String permissionName;

    private String description;

    private Boolean active = true;

    public PermissionRequest() {
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
