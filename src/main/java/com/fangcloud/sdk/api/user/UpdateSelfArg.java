package com.fangcloud.sdk.api.user;

import com.fangcloud.sdk.YfyArg;

public class UpdateSelfArg implements YfyArg {
    private String name;

    public UpdateSelfArg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
