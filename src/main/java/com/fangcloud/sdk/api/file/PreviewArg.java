package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviewArg implements YfyArg {
    private String kind;
    @JsonProperty("force_regenerate")
    private boolean forceRegenerate;

    public PreviewArg(String kind, boolean forceRegenerate) {
        this.kind = kind;
        this.forceRegenerate = forceRegenerate;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public boolean isForceRegenerate() {
        return forceRegenerate;
    }

    public void setForceRegenerate(boolean forceRegenerate) {
        this.forceRegenerate = forceRegenerate;
    }
}
