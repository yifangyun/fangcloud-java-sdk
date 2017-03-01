package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fangcloud.sdk.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewVersionPreSignatureUploadArg implements YfyArg {
    private String name;
    @JsonProperty("upload_type")
    private String uploadType;
    private String remark;

    public NewVersionPreSignatureUploadArg(String name, String uploadType, String remark) throws ClientValidationException {
        StringUtil.checkNameValid(name);
        this.name = name;
        this.uploadType = uploadType;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
