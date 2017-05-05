package com.fangcloud.sdk.api.comment;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyComment extends YfyBaseDTO {
    @JsonProperty("comment_id")
    private Long commentId;
    private String content;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("file_id")
    private Long fileId;
    private YfyMiniUser user;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public YfyMiniUser getUser() {
        return user;
    }

    public void setUser(YfyMiniUser user) {
        this.user = user;
    }
}
