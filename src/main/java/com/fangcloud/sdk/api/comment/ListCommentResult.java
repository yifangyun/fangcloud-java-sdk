package com.fangcloud.sdk.api.comment;

import com.fangcloud.sdk.api.YfyBaseDTO;

import java.util.List;

public class ListCommentResult extends YfyBaseDTO {
    private List<YfyComment> comments;

    public List<YfyComment> getComments() {
        return comments;
    }

    public void setComments(List<YfyComment> comments) {
        this.comments = comments;
    }
}
