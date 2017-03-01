package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fangcloud.sdk.util.StringUtil;

public class UpdateFolderArg implements YfyArg {
    private String name;

    public UpdateFolderArg(String name) throws ClientValidationException {
        StringUtil.checkNameValid(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
