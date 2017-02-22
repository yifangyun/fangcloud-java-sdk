package com.fangcloud.core.sdk.api.file;

import com.fangcloud.core.sdk.api.YfyBaseDTO;
import com.fangcloud.core.sdk.api.YfyMiniFile;
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
