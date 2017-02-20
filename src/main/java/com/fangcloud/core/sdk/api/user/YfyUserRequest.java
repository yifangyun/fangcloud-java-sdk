package com.fangcloud.core.sdk.api.user;

import com.fangcloud.core.sdk.YfyClient;
import com.fangcloud.core.sdk.exception.YfyException;

public class YfyUserRequest {
    private static String SELF_INFO_PATH = "api/user/info";

    private final YfyClient client;

    public YfyUserRequest(YfyClient client) {
        this.client = client;
    }

    /**
     * Get {@link YfyUser} model link with the access token
     *
     * @return {@link YfyUser} model link with the access token
     * @throws YfyException
     */
    public YfyUser getSelfInfo() throws YfyException {
        return client.doGet(client.getHost().getApi(),
                SELF_INFO_PATH,
                null,
                null,
                YfyUser.class);
    }
}
