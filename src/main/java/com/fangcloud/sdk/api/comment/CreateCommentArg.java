package com.fangcloud.sdk.api.comment;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCommentArg implements YfyArg {
    @JsonProperty("file_id")
    private Long fileId;
    private String content;

    public CreateCommentArg(long fileId, String content) {
        this.fileId = fileId;
        this.content = content;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
