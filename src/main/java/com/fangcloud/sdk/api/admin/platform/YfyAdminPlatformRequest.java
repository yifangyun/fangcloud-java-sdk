package com.fangcloud.sdk.api.admin.platform;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.admin.user.YfyDetailedUser;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyAdminPlatformRequest {
    private final static String ADMIN_PLATFORM_PATH = YfySdkConstant.API_VERSION + "admin/platform/";
    private final static String MAPPING_USER_PATH = ADMIN_PLATFORM_PATH + "%s/mapping_user";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyAdminPlatformRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Delete a user with handing over files. (Attention! This operation can not revoke.)
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param userId Platform user id in custom system（can be any string）
     * @return Detailed user information only seen by admin
     * @throws YfyException
     */
    public YfyDetailedUser getMappingUser(long platformId, final String userId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.USER_ID, userId);
        }};
        return getMappingUser(listParams, mapParams);
    }

    private YfyDetailedUser getMappingUser(String[] listParams, Map<String, String> mapParams) throws YfyException {
        return client.doGet(MAPPING_USER_PATH,
                listParams,
                mapParams,
                YfyDetailedUser.class);
    }
}
