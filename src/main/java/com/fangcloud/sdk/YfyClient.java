package com.fangcloud.sdk;

import com.fangcloud.sdk.api.collab.YfyCollabRequest;
import com.fangcloud.sdk.api.comment.YfyCommentRequest;
import com.fangcloud.sdk.api.department.YfyDepartmentRequest;
import com.fangcloud.sdk.api.file.YfyFileRequest;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.api.item.YfyItemRequest;
import com.fangcloud.sdk.api.share_link.YfyShareLinkRequest;
import com.fangcloud.sdk.api.trash.YfyTrashRequest;
import com.fangcloud.sdk.api.user.YfyUserRequest;

public class YfyClient<K> extends YfyBaseClient<K> {
    private final YfyFileRequest fileRequest;
    private final YfyUserRequest userRequest;
    private final YfyFolderRequest folderRequest;
    private final YfyItemRequest itemRequest;
    private final YfyShareLinkRequest shareLinkRequest;
    private final YfyCollabRequest collabRequest;
    private final YfyCommentRequest commentRequest;
    private final YfyTrashRequest trashRequest;
    private final YfyDepartmentRequest departmentRequest;

    public YfyClient(K key,
                     YfyRequestConfig requestConfig,
                     String accessToken,
                     String refreshToken,
                     YfyRefreshListener<K> refreshListener) {
        super(key, requestConfig, accessToken, refreshToken, refreshListener);
        YfyInternalClient internalClient = new YfyInternalClient();
        this.fileRequest = new YfyFileRequest(internalClient);
        this.userRequest = new YfyUserRequest(internalClient);
        this.folderRequest = new YfyFolderRequest(internalClient);
        this.itemRequest = new YfyItemRequest(internalClient);
        this.shareLinkRequest = new YfyShareLinkRequest(internalClient);
        this.collabRequest = new YfyCollabRequest(internalClient);
        this.commentRequest = new YfyCommentRequest(internalClient);
        this.trashRequest = new YfyTrashRequest(internalClient);
        this.departmentRequest = new YfyDepartmentRequest(internalClient);
    }

    public YfyClient(K key, YfyRequestConfig requestConfig, String accessToken, String refreshToken) {
        this(key, requestConfig, accessToken, refreshToken, null);
    }

    public YfyClient(YfyRequestConfig requestConfig, String accessToken) {
        this(null, requestConfig, accessToken, null);
    }

    public YfyFileRequest files() {
        return fileRequest;
    }

    public YfyUserRequest users() {
        return userRequest;
    }

    public YfyFolderRequest folders() {
        return folderRequest;
    }

    public YfyItemRequest items() {
        return itemRequest;
    }

    public YfyShareLinkRequest shareLinks() {
        return shareLinkRequest;
    }

    public YfyCollabRequest collabs() {
        return collabRequest;
    }

    public YfyTrashRequest trashs() {
        return trashRequest;
    }

    public YfyCommentRequest comments() {
        return commentRequest;
    }

    public YfyDepartmentRequest departments() {
        return departmentRequest;
    }

}
