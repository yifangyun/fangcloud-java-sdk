package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.util.StringUtil;

public class UpdateFileArg implements YfyArg {
    private String name;
    private String description;

    public UpdateFileArg(String name, String description) {
        StringUtil.checkNameValid(name);

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
