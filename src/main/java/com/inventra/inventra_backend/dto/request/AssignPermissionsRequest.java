package com.inventra.inventra_backend.dto.request;

import java.util.List;

public class AssignPermissionsRequest {

    private List<String> permissionIds;

    public AssignPermissionsRequest() {
    }

    public List<String> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
