package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fangcloud.sdk.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFolderArg implements YfyArg {
    private String name;
    @JsonProperty("parent_id")
    private long parentId;

    public CreateFolderArg(String name, long parentId) throws ClientValidationException {
        StringUtil.checkNameValid(name);
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
