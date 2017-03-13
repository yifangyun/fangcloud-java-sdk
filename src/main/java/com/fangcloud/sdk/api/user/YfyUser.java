package com.fangcloud.sdk.api.user;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyUser extends YfyBaseDTO {
    private Long id;
    @JsonProperty("enterprise_id")
    private Long enterpriseId;
    private String name;
    private String phone;
    private String email;
    @JsonProperty("profile_pic_key")
    private String profilePicKey;
    private Boolean active;
    @JsonProperty("full_name_pinyin")
    private String fullNamePinyin;
    @JsonProperty("pinyin_first_letters")
    private String pinyinFirstLetters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
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

    public String getProfilePicKey() {
        return profilePicKey;
    }

    public void setProfilePicKey(String profilePicKey) {
        this.profilePicKey = profilePicKey;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
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
