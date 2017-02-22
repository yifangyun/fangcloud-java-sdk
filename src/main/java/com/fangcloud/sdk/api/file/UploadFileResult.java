package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniFile;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadFileResult extends YfyBaseDTO {
    @JsonProperty("new_file")
    private YfyMiniFile newFile;

    public YfyMiniFile getNewFile() {
        return newFile;
    }

    public void setNewFile(YfyMiniFile newFile) {
        this.newFile = newFile;
    }
}
