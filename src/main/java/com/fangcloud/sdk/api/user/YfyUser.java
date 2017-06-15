package com.fangcloud.sdk.api.user;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.enterprise.YfyEnterprise;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyUser extends YfyBaseDTO {
    private Long id;
    private YfyEnterprise enterprise;
    private String name;
    private String login;
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

    public YfyEnterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(YfyEnterprise enterprise) {
        this.enterprise = enterprise;
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
