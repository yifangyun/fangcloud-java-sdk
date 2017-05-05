package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InviteCollabArg implements YfyArg {
    @JsonProperty("folder_id")
    private Long folderId;
    @JsonProperty("invited_user")
    private InvitedUserIdAndRole invitedUser;
    @JsonProperty("invitation_message")
    private String invitationMessage;

    public InviteCollabArg(long folderId, long userId, String role, String invitationMessage) {
        this.folderId = folderId;
        this.invitedUser = new InvitedUserIdAndRole(userId, role);
        this.invitationMessage = invitationMessage;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public InvitedUserIdAndRole getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(InvitedUserIdAndRole invitedUser) {
        this.invitedUser = invitedUser;
    }

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public void setInvitationMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    private class InvitedUserIdAndRole {
        private long id;
        private String role;

        public InvitedUserIdAndRole(long userId, String role) {
            this.id = userId;
            this.role = role;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
