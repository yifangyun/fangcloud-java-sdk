package com.fangcloud.sdk.api.share_link;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateShareLinkArg implements YfyArg {
    @JsonProperty("folder_id")
    private Long folderId;

    @JsonProperty("file_id")
    private Long fileId;

    private String access;

    @JsonProperty("disable_download")
    private boolean disableDownload;

    @JsonProperty("due_time")
    private String dueTime;

    @JsonProperty("password_protected")
    private boolean passwordProtected;

    private String password;

    public CreateShareLinkArg(Long folderId, Long fileId, String access, boolean disableDownload, String dueTime,
                              boolean passwordProtected, String password) {
        this.folderId = folderId;
        this.fileId = fileId;
        this.access = access;
        this.disableDownload = disableDownload;
        this.dueTime = dueTime;
        this.passwordProtected = passwordProtected;
        this.password = password;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean isDisableDownload() {
        return disableDownload;
    }

    public void setDisableDownload(boolean disableDownload) {
        this.disableDownload = disableDownload;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public boolean isPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
