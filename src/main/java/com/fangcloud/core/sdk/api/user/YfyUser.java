package com.fangcloud.core.sdk.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyUser {
    private long id;
    @JsonProperty("enterprise_id")
    private long enterpriseId;
    private String name;
    private String phone;
    private String email;
    @JsonProperty("profile_pic_key")
    private String profilePicPath;
    private boolean active;
    @JsonProperty("full_name_pinyin")
    private String fullNamePinyin;
    @JsonProperty("pinyin_first_letters")
    private String pinyinFirstLetters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFullNamePinyin() {
        return fullNamePinyin;
    }

    public void setFullNamePinyin(String fullNamePinyin) {
        this.fullNamePinyin = fullNamePinyin;
    }

    public String getPinyinFirstLetters() {
        return pinyinFirstLetters;
    }

    public void setPinyinFirstLetters(String pinyinFirstLetters) {
        this.pinyinFirstLetters = pinyinFirstLetters;
    }
}
