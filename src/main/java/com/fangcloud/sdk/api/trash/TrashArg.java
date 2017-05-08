package com.fangcloud.sdk.api.trash;

import com.fangcloud.sdk.YfyArg;

public class TrashArg implements YfyArg {
    private String type;

    public TrashArg(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
