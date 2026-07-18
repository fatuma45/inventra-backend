package com.inventra.inventra_backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RoleRequest {

    @NotBlank
    private String roleName;

    private String description;

    private Boolean active = true;

    public RoleRequest() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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