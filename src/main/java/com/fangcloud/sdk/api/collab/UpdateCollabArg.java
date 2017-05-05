package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.YfyArg;

public class UpdateCollabArg implements YfyArg {
    private String role;

    public UpdateCollabArg(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
