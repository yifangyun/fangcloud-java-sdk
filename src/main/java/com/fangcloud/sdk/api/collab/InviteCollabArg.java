package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InviteCollabArg implements YfyArg {
    @JsonProperty("folder_id")
    private Long folderId;
    @JsonProperty("accessible_by")
    private AccessibleBy accessibleBy;
    @JsonProperty("invitation_message")
    private String invitationMessage;

    public InviteCollabArg(long folderId, String accessibleByType, long accessibleById, String role, String invitationMessage) {
        this.folderId = folderId;
        this.accessibleBy = new AccessibleBy(accessibleByType, accessibleById, role);
        this.invitationMessage = invitationMessage;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public AccessibleBy getAccessibleBy() {
        return accessibleBy;
    }

    public void setAccessibleBy(AccessibleBy accessibleBy) {
        this.accessibleBy = accessibleBy;
    }

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public void setInvitationMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    public static class AccessibleBy {
        private String type;
        private Long id;
        private String role;

        public AccessibleBy(String type, Long id, String role) {
            this.type = type;
            this.id = id;
            this.role = role;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
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
