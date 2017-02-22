package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyMiniUser {
    private long id;
    private String name;
    private String login;
    private String phone;
    @JsonProperty("profile_pic_key")
    private String profilePicKey;
    @JsonProperty("enterprise_id")
    private long enterpriseId;
    private Boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getProfilePicKey() {
        return profilePicKey;
    }

    public void setProfilePicKey(String profilePicKey) {
        this.profilePicKey = profilePicKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
