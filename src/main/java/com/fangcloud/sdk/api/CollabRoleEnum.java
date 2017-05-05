package com.fangcloud.sdk.api;

public enum CollabRoleEnum {
    EDITOR("editor"),
    VIEWER("viewer"),
    PREVIEWER("previewer"),
    UPLOADER("uploader"),
    PREVIEWER_UPLOADER("previewer_uploader"),
    COOWNER("coowner"),
    VIEWER_UPLOADER("viewer_uploader");

    private String role;

    CollabRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
